/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.knowledgebodies;

import java.util.List;
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
import pt.cmg.sweranker.api.rest.resources.knowledgebodies.converter.KnowledgeAreaConverter;
import pt.cmg.sweranker.api.rest.resources.knowledgebodies.converter.KnowledgeTopicConverter;
import pt.cmg.sweranker.dao.knowledgeareas.KnowledgeAreaDAO;
import pt.cmg.sweranker.persistence.entities.knowledgebodies.KnowledgeArea;

/**
 * @author Carlos Gonçalves
 */
@Path("knowledgeareas")
public class KnowledgeAreaResource {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private KnowledgeAreaDAO knowledgeAreaDAO;

    @Inject
    private KnowledgeAreaConverter kaConverter;

    @Inject
    private KnowledgeTopicConverter kaTopicConverter;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<KnowledgeArea> knowledgeAreas = knowledgeAreaDAO.findAll();
        return Response.ok(kaConverter.toKnowledgeAreaDTOs(knowledgeAreas)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKnowledgeArea(@PathParam("id") Long id) {
        KnowledgeArea ka = knowledgeAreaDAO.findById(id);
        return Response.ok(kaConverter.toDetailedKnowledgeAreaDTO(ka)).build();
    }

    @GET
    @Path("{id}/topics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopicsOfKnowledgeArea(@PathParam("id") Long knowledgeAreaId) {

        var knowledgeArea = knowledgeAreaDAO.findById(knowledgeAreaId);

        if (knowledgeArea == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        return Response.ok(kaTopicConverter.toTopicDTOs(knowledgeArea.getKnowledgeTopics())).build();
    }

}
