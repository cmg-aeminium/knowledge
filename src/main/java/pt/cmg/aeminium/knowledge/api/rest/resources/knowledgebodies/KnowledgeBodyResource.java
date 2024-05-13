package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter.KnowledgeAreaConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter.KnowledgeBodyConverter;
import pt.cmg.aeminium.knowledge.dao.knowledgeareas.KnowledgeBodyDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeBody;
import pt.cmg.jakartautils.errors.ErrorDTO;

@Path("knowledgebodies")
@Stateless
public class KnowledgeBodyResource {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private KnowledgeBodyDAO knowledgeBodyDAO;

    @Inject
    private KnowledgeBodyConverter knowledgeBodyConverter;;

    @Inject
    private KnowledgeAreaConverter kaConverter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<KnowledgeBody> knowledgeBodies = knowledgeBodyDAO.findAll();
        return Response.ok(knowledgeBodyConverter.toKnowledgeBodyDTOs(knowledgeBodies)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        KnowledgeBody bodyOfKnowledge = knowledgeBodyDAO.findById(id);

        if (bodyOfKnowledge == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1)).build();
        }

        return Response.ok(knowledgeBodyConverter.toKnowledgeBodyDTO(bodyOfKnowledge)).build();
    }

    @GET
    @Path("{id}/knowledgeareas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKnowledgeAreasOfBody(@PathParam("id") Long id) {
        KnowledgeBody bodyOfKnowledge = knowledgeBodyDAO.findById(id);

        if (bodyOfKnowledge == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1)).build();
        }
        return Response.ok(kaConverter.toKnowledgeAreaDTOs(bodyOfKnowledge.getKnowledgeAreas())).build();
    }

}
