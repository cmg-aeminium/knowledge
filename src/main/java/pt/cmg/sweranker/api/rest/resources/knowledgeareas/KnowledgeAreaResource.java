package pt.cmg.sweranker.api.rest.resources.knowledgeareas;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import pt.cmg.sweranker.api.rest.filters.request.RequestContextData;
import pt.cmg.sweranker.api.rest.filters.request.RequestData;
import pt.cmg.sweranker.api.rest.resources.knowledgeareas.converter.KnowledgeAreaConverter;
import pt.cmg.sweranker.api.rest.resources.knowledgeareas.converter.KnowledgeTopicConverter;
import pt.cmg.sweranker.dao.knowledgeareas.KnowledgeAreaDAO;
import pt.cmg.sweranker.persistence.entities.knowledgebodies.KnowledgeArea;

@Path("knowledgreareas")
@Stateless
public class KnowledgeAreaResource {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private KnowledgeAreaDAO knowledgeAreaDAO;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKnowledgeArea(@PathParam("id") Long id) {
        KnowledgeArea ka = knowledgeAreaDAO.findById(id);
        return Response.ok(KnowledgeAreaConverter.toDetailedKnowledgeAreaDTO(ka)).build();
    }

    @GET
    @Path("{id}/topics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopicsOfKnowledgeArea(@PathParam("id") Long knowledgeAreaId) {

        var knowledgeArea = knowledgeAreaDAO.findById(knowledgeAreaId);

        if (knowledgeArea == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        return Response.ok(KnowledgeTopicConverter.toTopicDTOs(knowledgeArea.getKnowledgeTopics())).build();
    }

}
