/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.CourseConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CourseSearchFilter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators.CourseValidator;
import pt.cmg.aeminium.knowledge.dao.schools.CourseClassDAO;
import pt.cmg.aeminium.knowledge.dao.schools.CourseDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.Course;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClass;
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

        var validationErrors = courseValidator.isCreationValid(newCourseDTO);
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

        var validationErrors = courseValidator.isEditionValid(courseEditionDTO, id);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        Course newSchool = courseCreator.editCourse(courseEditionDTO, id);

        return Response.ok(courseConverter.toCourseDTO(newSchool)).build();
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

        return Response.ok(courseConverter.toCourseClassesDTO(course.getCourseClasses())).build();
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

        return Response.ok(courseConverter.toCourseClassesDTO(courseClass)).build();
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

        return Response.ok(courseConverter.toCourseClassDTO(newClass)).build();
    }
}
