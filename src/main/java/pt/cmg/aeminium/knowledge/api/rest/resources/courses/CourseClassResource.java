/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses;

import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClass;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClassTopic;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.CourseClassConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators.CourseValidator;
import pt.cmg.aeminium.knowledge.tasks.courses.CourseCreator;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@Path("courseclasses")
@Stateless
public class CourseClassResource {

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
    public Response getAllCourseClasses() {

        List<CourseClass> courseClasses = courseClassDAO.findAll();

        return Response.ok(courseClassConverter.toCourseClassesDTO(courseClasses)).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response deleteCourseClassTopic(@PathParam("id") Long id, @PathParam("topicId") Long topicId) {
        courseCreator.deleteTopic(topicId);
        return Response.ok().build();
    }
}
