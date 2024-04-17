/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.courses;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.cmg.sweranker.api.rest.filters.request.RequestContextData;
import pt.cmg.sweranker.api.rest.filters.request.RequestData;
import pt.cmg.sweranker.api.rest.resources.courses.converters.SchoolConverter;
import pt.cmg.sweranker.dao.schools.SchoolDAO;
import pt.cmg.sweranker.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
@Path("schools")
@Stateless
public class SchoolResource {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @EJB
    private SchoolDAO schoolDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<School> schools = schoolDAO.findAll();
        return Response.ok(SchoolConverter.toSchoolDTOs(schools)).build();
    }

}
