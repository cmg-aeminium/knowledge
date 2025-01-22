/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses;

import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
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
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.SchoolDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.SchoolDAO.SchoolFilter;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.School;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.CourseConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters.SchoolConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateSchoolDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditSchoolDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.SearchSchoolFilterDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators.SchoolValidator;
import pt.cmg.aeminium.knowledge.tasks.schools.SchoolCreator;
import pt.cmg.jakartautils.errors.ErrorDTO;
import pt.cmg.jakartautils.pagination.PaginatedDTO;

/**
 * @author Carlos Gonçalves
 */
@Path("schools")
@RequestScoped
public class SchoolResource {

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
    @Transactional(value = TxType.REQUIRED)
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
    @Transactional(value = TxType.REQUIRED)
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
    public Response getAllFiltered(@Valid @BeanParam SearchSchoolFilterDTO filter) {

        var validationErrors = schoolValidator.isSearchValid(filter);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        List<School> schools = schoolDAO.findByFiltered(new SchoolFilter(filter.countryIds, filter.size, filter.offset));
        int filterCount = schoolDAO.countFiltered(new SchoolFilter(filter.countryIds, filter.size, filter.offset));

        return Response.ok(new PaginatedDTO<>(filterCount, schoolConverter.toSchoolDTOs(schools))).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {

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
