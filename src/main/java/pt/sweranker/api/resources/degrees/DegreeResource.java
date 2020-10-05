/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.api.resources.degrees;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.sweranker.api.resources.degrees.converters.DegreeConverter;
import pt.sweranker.api.resources.degrees.dto.request.DegreeSearchFilter;
import pt.sweranker.dao.degrees.DegreeDAO;
import pt.sweranker.filters.request.RequestContextData;
import pt.sweranker.filters.request.RequestData;
import pt.sweranker.persistence.entities.degrees.DegreeTranslation;

/**
 * @author Carlos Gonçalves
 */
@Path("degrees")
@Stateless
public class DegreeResource {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @EJB
    private DegreeDAO degreeDAO;

    @EJB
    private DegreeConverter degreeConverter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Valid @BeanParam DegreeSearchFilter filter) {
        List<DegreeTranslation> degrees = degreeDAO.findFiltered(degreeConverter.toDegreeFilterCriteria(filter));
        return Response.ok(degreeConverter.toDegreeDTOs(degrees)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDegree(@PathParam("id") Long id) {

        DegreeTranslation degree = degreeDAO.findById(id);

        return Response.ok(degreeConverter.toDegreeDTO(degree)).build();
    }
}
