/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.SchoolConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateSchoolDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditSchoolDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators.SchoolValidator;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.School;
import pt.cmg.aeminium.knowledge.tasks.schools.SchoolCreator;
import pt.cmg.jakartautils.errors.ErrorDTO;

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

    @Inject
    private SchoolValidator schoolValidator;

    @Inject
    private SchoolCreator schoolCreator;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    public Response createSchool(CreateSchoolDTO newSchoolDTO) {

        var validationErrors = schoolValidator.isCreationValid(newSchoolDTO);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        School newSchool = schoolCreator.createSchool(newSchoolDTO);

        return Response.ok(schoolConverter.toSchoolDTO(newSchool)).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    public Response editSchool(@PathParam("id") Long id, EditSchoolDTO schoolEditionDTO) {

        var validationErrors = schoolValidator.isEditionValid(schoolEditionDTO, id);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        School newSchool = schoolCreator.editSchool(schoolEditionDTO, id);

        return Response.ok(schoolConverter.toSchoolDTO(newSchool)).build();
    }

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
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "School does not exist")).build();
        }

        return Response.ok(schoolConverter.toSchoolDTO(school)).build();
    }

    @GET
    @Path("/{id}/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursesOfSchool(@PathParam("id") Long id) {
        School school = schoolDAO.findById(id);

        if (school == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "School does not exist")).build();
        }

        return Response.ok(courseConverter.toCourseDTOs(school.getCourses())).build();
    }

}
