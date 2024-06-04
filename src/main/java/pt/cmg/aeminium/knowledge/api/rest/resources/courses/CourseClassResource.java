/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.CourseConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators.CourseValidator;
import pt.cmg.aeminium.knowledge.dao.schools.CourseClassDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClass;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClassTopic;
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
    private CourseConverter courseConverter;

    @Inject
    private CourseValidator courseValidator;

    @Inject
    private CourseCreator courseCreator;

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourseById(@PathParam("id") Long id) {

        CourseClass course = courseClassDAO.findById(id);

        if (course == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Course Class does not exist")).build();
        }

        return Response.ok(courseConverter.toCourseClassDTO(course)).build();
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

        return Response.ok(courseConverter.toCourseClassDTO(newClass)).build();
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

        return Response.ok(courseConverter.toCourseClasseTopicDTOs(courseClass.getCourseClassTopics())).build();
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

        return Response.ok(courseConverter.toCourseClasseTopicDTO(newTopic)).build();
    }
}
