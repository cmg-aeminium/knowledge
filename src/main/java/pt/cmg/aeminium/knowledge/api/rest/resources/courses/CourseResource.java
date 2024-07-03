/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses;

import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.CourseClassDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.CourseDAO;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.Course;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClass;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.CourseClassConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.CourseConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CourseSearchFilter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators.CourseValidator;
import pt.cmg.aeminium.knowledge.tasks.courses.CourseCreator;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@Path("courses")
@Stateless
public class CourseResource {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @EJB
    private CourseDAO courseDAO;

    @EJB
    private CourseClassDAO courseClassDAO;

    @Inject
    private CourseConverter courseConverter;

    @Inject
    private CourseClassConverter courseClassConverter;

    @Inject
    private CourseValidator courseValidator;

    @Inject
    private CourseCreator courseCreator;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Valid @BeanParam CourseSearchFilter filter) {

        var validationErrors = courseValidator.isSearchFilterValid(filter);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        List<Course> degrees = courseDAO.findFiltered(courseConverter.toDegreeFilterCriteria(filter));

        return Response.ok(courseConverter.toCourseDTOs(degrees)).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseById(@PathParam("id") Long id) {

        Course course = courseDAO.findById(id);

        if (course == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Course does not exist")).build();
        }

        return Response.ok(courseConverter.toCourseDTO(course)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    public Response createCourse(@Valid CreateCourseDTO newCourseDTO) {

        var validationErrors = courseValidator.isCourseCreationValid(newCourseDTO);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        Course newCourse = courseCreator.createCourse(newCourseDTO);

        return Response.ok(courseConverter.toCourseDTO(newCourse)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    public Response editCourse(@PathParam("id") Long id, EditCourseDTO courseEditionDTO) {

        var validationErrors = courseValidator.isCourseEditionValid(courseEditionDTO, id);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        Course newCourse = courseCreator.editCourse(courseEditionDTO, id);

        return Response.ok(courseConverter.toCourseDTO(newCourse)).build();
    }

    @GET
    @Path("{id}/classes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseClasses(@PathParam("id") Long id) {

        Course course = courseDAO.findById(id);

        if (course == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Course does not exist")).build();
        }

        return Response.ok(courseClassConverter.toCourseClassesDTO(course.getCourseClasses())).build();
    }

    @GET
    @Path("{id}/classes/{classId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseClassTopics(@PathParam("id") Long id, @PathParam("classId") Long classId) {

        Course course = courseDAO.findById(id);
        if (course == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Course does not exist")).build();
        }

        CourseClass courseClass = courseClassDAO.findById(classId);
        if (courseClass == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(2, "Course Class does not exist")).build();
        }

        if (!courseClass.getCourse().getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(3, "This class does not belong to this degree")).build();
        }

        return Response.ok(courseClassConverter.toCourseClassDTO(courseClass)).build();
    }

    @POST
    @Path("{id}/classes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    public Response createCourseClass(@PathParam("id") Long id, @Valid CreateCourseClassDTO newClassDTO) {

        var validationErrors = courseValidator.isClassCreationValid(newClassDTO, id);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        CourseClass newClass = courseCreator.createClass(newClassDTO, id);

        return Response.ok(courseClassConverter.toCourseClassDetailedDTO(newClass)).build();
    }

    @PUT
    @Path("{id}/classes/{classId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    public Response editCourseClass(@PathParam("id") Long courseId, @PathParam("classId") Long classId, EditCourseClassDTO editCourseClassDTO) {

        var validationErrors = courseValidator.isCourseClassEditionValid(courseId, classId, editCourseClassDTO);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        CourseClass editedClass = courseCreator.editClass(editCourseClassDTO, classId);

        return Response.ok(courseClassConverter.toCourseClassDetailedDTO(editedClass)).build();
    }
}
