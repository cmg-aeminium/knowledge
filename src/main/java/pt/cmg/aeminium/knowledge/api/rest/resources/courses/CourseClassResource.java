/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses;

import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.CourseClassDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.CourseClassDAO.CourseClassFilterCriteria;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClass;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClassTopic;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.CourseClassConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.SearchCourseClassFilterDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators.CourseValidator;
import pt.cmg.aeminium.knowledge.tasks.courses.CourseCreator;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@Path("courseclasses")
@RequestScoped
@Tag(name = "Courses Classes", description = "Endpoints related operations with Course Classes")
public class CourseClassResource {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @EJB
    private CourseClassDAO courseClassDAO;

    @Inject
    private CourseClassConverter courseClassConverter;

    @Inject
    private CourseValidator courseValidator;

    @Inject
    private CourseCreator courseCreator;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Searches for Course Classes",
        description = "Obtains a paginated list of classes given a set of input filters",
        operationId = "GET_courseclasses_filterd")
    @APIResponse(
        responseCode = "200",
        description = "Returns a course class list that matches the search criteria",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, ref = "#/components/schemas/CourseClassDTO")))
    @APIResponse(
        responseCode = "400",
        description = "User with id does not exist.  Returns a list of the Errors",
        content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(type = SchemaType.ARRAY, implementation = ErrorDTO.class),
            example = """
                        [
                            {
                            "code": 1001,
                            "description": "Email cannot be null or empty"
                            },
                            {
                            "code": 1002,
                            "description": "Name cannot be null or empty"
                            }
                        ]
                """))
    public Response getAllClassesFiltered(@Valid @BeanParam SearchCourseClassFilterDTO filter) {

        var validationErrors = courseValidator.isCourseClassSearchFilterValid(filter);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        List<CourseClass> courseClasses = courseClassDAO.findFiltered(
            new CourseClassFilterCriteria(filter.year,
                filter.semester,
                filter.isOptional,
                filter.name,
                requestData.getSelectedLanguage(),
                filter.course,
                filter.school,
                filter.size,
                filter.offset));

        return Response.ok(courseClassConverter.toCourseClassesDTO(courseClasses)).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Retrieves a Course Class by the id",
        description = "Obtains Class data for a given identification number",
        operationId = "GET_courseclass_by_id")
    @APIResponse(
        responseCode = "200",
        description = "Course Class found. Return data.",
        content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/CourseClassDetailDTO")))
    @APIResponse(
        responseCode = "400",
        description = "Course Class with id does not exist.  Returns a list of the Errors",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    public Response getClassById(@PathParam("id") Long id) {

        CourseClass course = courseClassDAO.findById(id);

        if (course == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Course Class does not exist")).build();
        }

        return Response.ok(courseClassConverter.toCourseClassDetailedDTO(course)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    @Transactional(value = TxType.REQUIRED)
    @Operation(
        summary = "Creates a new Course Class",
        description = "Creates a new Course Class, if authorized to do so.",
        operationId = "POST_courseclasses")
    @APIResponse(
        responseCode = "200",
        description = "Returns the newly created Course Class",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "#/components/schemas/CourseClassDetailDTO")))
    @APIResponse(
        responseCode = "400",
        description = "A number of input parameters were not fit to create a Course Class. Returns a list of the Errors",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = ErrorDTO.class)))
    public Response createCourseClass(@Valid CreateCourseClassDTO newClassDTO) {

        var validationErrors = courseValidator.isClassCreationValid(newClassDTO);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        CourseClass newClass = courseCreator.createClass(newClassDTO);

        return Response.ok(courseClassConverter.toCourseClassDetailedDTO(newClass)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    @Transactional(value = TxType.REQUIRED)
    @Operation(
        summary = "Edits a Course Class",
        description = "Edits the Course Class details, if authorized to do so",
        operationId = "PUT_courseclasses")
    @APIResponse(
        responseCode = "200",
        description = "Returns the edited Course Class",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "#/components/schemas/CourseClassDTO")))
    @APIResponse(
        responseCode = "400",
        description = "A number of input parameters were not fit to edit Course Class. Returns a list of the Errors",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = ErrorDTO.class)))
    public Response editCourseClass(@PathParam("id") Long id, @Valid EditCourseClassDTO newClassDTO) {

        var validationErrors = courseValidator.isClassEditionValid(id, newClassDTO);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        CourseClass newClass = courseCreator.editClass(newClassDTO, id);

        return Response.ok(courseClassConverter.toCourseClassDTO(newClass)).build();
    }

    @GET
    @Path("/{id}/topics")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Retrieves the topics of the Course Class by the id",
        description = "Obtains the class topics for a given identification number",
        operationId = "GET_courseclasstopics_by_classid")
    @APIResponse(
        responseCode = "200",
        description = "Course Class exists. Return topics data.",
        content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, ref = "#/components/schemas/CourseClassDetailDTO")))
    @APIResponse(
        responseCode = "400",
        description = "Course Class with id does not exist.  Returns a list of the Errors",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    public Response getCourseClassTopics(@PathParam("id") Long id) {

        CourseClass courseClass = courseClassDAO.findById(id);
        if (courseClass == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Course Class does not exist")).build();
        }

        return Response.ok(courseClassConverter.toCourseClasseTopicDTOs(courseClass.getCourseClassTopics())).build();
    }

    @POST
    @Path("/{id}/topics")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(value = TxType.REQUIRED)
    @Operation(
        summary = "Creates a new Topic",
        description = "Creates a new Topic for a given Course Class, if authorized to do so.",
        operationId = "POST_courseclassestopic_of_class")
    @APIResponse(
        responseCode = "200",
        description = "Returns the newly created Course Class Topic",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "#/components/schemas/CourseClassTopicDTO")))
    @APIResponse(
        responseCode = "400",
        description = "A number of input parameters were not fit to create a Course Class topic. Returns a list of the Errors",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = ErrorDTO.class)))
    public Response createCourseClassTopics(@PathParam("id") Long id, @Valid CreateCourseClassTopicDTO topicDTO) {

        CourseClass courseClass = courseClassDAO.findById(id);
        if (courseClass == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Course Class does not exist")).build();
        }

        CourseClassTopic newTopic = courseCreator.createTopic(topicDTO, id);

        return Response.ok(courseClassConverter.toCourseClasseTopicDTO(newTopic)).build();
    }

    @PUT
    @Path("/{id}/topics/{topicId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(value = TxType.REQUIRED)
    @Operation(
        summary = "Edits a Topic",
        description = "Edits the Topic details of a given Course Class, if authorized to do so",
        operationId = "PUT_courseclassestopic_of_class")
    @APIResponse(
        responseCode = "200",
        description = "Returns the edited Course Class Topic",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "#/components/schemas/CourseClassTopicDTO")))
    @APIResponse(
        responseCode = "400",
        description = "A number of input parameters were not fit to edit Course Class. Returns a list of the Errors",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = ErrorDTO.class)))
    public Response editCourseClassTopic(@PathParam("id") Long id, @PathParam("topicId") Long topicId, EditCourseClassTopicDTO editTopicDTO) {

        var validationErrors = courseValidator.isTopicEditionValid(id, topicId, editTopicDTO);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        CourseClassTopic newTopic = courseCreator.editTopic(editTopicDTO, topicId);

        return Response.ok(courseClassConverter.toCourseClasseTopicDTO(newTopic)).build();
    }

    @DELETE
    @Path("/{id}/topics/{topicId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(value = TxType.REQUIRED)
    @Operation(
        summary = "Deletes a Topic",
        description = "Deletes the Topic of a given Course Class, if authorized to do so",
        operationId = "DELETE_courseclassestopic_of_class")
    @APIResponse(
        responseCode = "200",
        description = "Success")
    public Response deleteCourseClassTopic(@PathParam("id") Long id, @PathParam("topicId") Long topicId) {
        courseCreator.deleteTopic(topicId);
        return Response.ok().build();
    }
}
