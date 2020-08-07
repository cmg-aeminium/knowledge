package pt.sweranker.api.resources.knowledgeareas;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.sweranker.api.resources.knowledgeareas.converter.KnowledgeAreaConverter;
import pt.sweranker.dao.knowledgeareas.KnowledgeAreaDAO;
import pt.sweranker.filters.request.RequestData;
import pt.sweranker.filters.request.RequestPayload;

@Path("knowledgreareas")
@Stateless
public class KnowledgeAreaResource {

    @EJB
    private KnowledgeAreaDAO knowledgeAreaDAO;

    @EJB
    private KnowledgeAreaConverter knowledgeAreaConverter;

    @Inject
    @RequestData
    private RequestPayload requestData;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt(@PathParam("id") Long id) {

        var ka = knowledgeAreaDAO.findById(id, requestData.getSelectedLanguage());

        return Response.ok(knowledgeAreaConverter.toDetailedKnowledgeAreaDTO(ka)).build();
    }

}
