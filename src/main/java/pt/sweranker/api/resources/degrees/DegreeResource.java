/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.api.resources.degrees;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.sweranker.api.resources.degrees.converters.DegreeConverter;
import pt.sweranker.dao.degrees.DegreeDAO;
import pt.sweranker.persistence.entities.degrees.DegreeTranslation;

/**
 * @author Carlos Gonçalves
 */
@Path("degrees")
@Stateless
public class DegreeResource {

    @EJB
    private DegreeDAO degreeDAO;

    @EJB
    private DegreeConverter degreeConverter;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKnoledgeArea(@PathParam("id") Long id) {

        DegreeTranslation degree = degreeDAO.findById(id);

        return Response.ok(degreeConverter.toDegreeDTO(degree)).build();
    }
}
