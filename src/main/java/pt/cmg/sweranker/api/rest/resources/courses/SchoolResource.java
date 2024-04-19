/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.courses;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.cmg.jakartautils.errors.ErrorDTO;
import pt.cmg.sweranker.api.rest.filters.request.RequestContextData;
import pt.cmg.sweranker.api.rest.filters.request.RequestData;
import pt.cmg.sweranker.api.rest.resources.courses.converters.CourseConverter;
import pt.cmg.sweranker.api.rest.resources.courses.converters.SchoolConverter;
import pt.cmg.sweranker.dao.schools.SchoolDAO;
import pt.cmg.sweranker.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
@Path("schools")
@Stateless
public class SchoolResource {

    private static final Logger LOGGER = Logger.getLogger(SchoolResource.class.getName());

    @Inject
    @RequestData
    private RequestContextData requestData;

    @EJB
    private SchoolDAO schoolDAO;

    @Inject
    private CourseConverter courseConverter;

    @Inject
    private SchoolConverter schoolConverter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<School> schools = schoolDAO.findAll();
        return Response.ok(schoolConverter.toSchoolDTOs(schools)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSchoolById(@PathParam("id") Long id) {

        School school = schoolDAO.findById(id);

        if (school == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1)).build();
        }

        return Response.ok(schoolConverter.toSchoolDTO(school)).build();
    }

    @GET
    @Path("/{id}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursesOfSchool(@PathParam("id") Long id) {
        School school = schoolDAO.findById(id);

        if (school == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1)).build();
        }

        return Response.ok(courseConverter.toCourseDTOs(school.getCourses())).build();
    }

}
