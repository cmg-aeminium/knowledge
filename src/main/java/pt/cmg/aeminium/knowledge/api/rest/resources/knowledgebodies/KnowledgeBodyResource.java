package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter.KnowledgeAreaConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter.KnowledgeBodyConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeBodyDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.validators.KnowledgeAreaValidator;
import pt.cmg.aeminium.knowledge.dao.knowledgeareas.KnowledgeBodyDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeArea;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeBody;
import pt.cmg.aeminium.knowledge.tasks.knowledgebodies.KnowledgeBodyCreator;
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
    private KnowledgeBodyConverter knowledgeBodyConverter;

    @Inject
    private KnowledgeAreaConverter knowledgeAreaConverter;

    @Inject
    private KnowledgeAreaConverter kaConverter;

    @Inject
    private KnowledgeBodyCreator knowledgeBodyCreator;

    @Inject
    private KnowledgeAreaValidator knowledgeAreaValidator;

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
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Body of Knowledge does not exist")).build();
        }

        return Response.ok(knowledgeBodyConverter.toKnowledgeBodyDTO(bodyOfKnowledge)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    public Response createKnowledgeBody(@Valid CreateKnowledgeBodyDTO newBOKDTO) {

        KnowledgeBody newKnowledgeBody = knowledgeBodyCreator.createKnowledgeBody(newBOKDTO);

        return Response.ok(knowledgeBodyConverter.toKnowledgeBodyDTO(newKnowledgeBody)).build();
    }

    @GET
    @Path("{id}/knowledgeareas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKnowledgeAreasOfBody(@PathParam("id") Long id) {
        KnowledgeBody bodyOfKnowledge = knowledgeBodyDAO.findById(id);

        if (bodyOfKnowledge == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Body of Knowledge does not exist")).build();
        }
        return Response.ok(kaConverter.toKnowledgeAreaDTOs(bodyOfKnowledge.getKnowledgeAreas())).build();
    }

    @POST
    @Path("{id}/knowledgeareas")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    public Response createKnowledgeArea(@PathParam("id") Long id, @Valid CreateKnowledgeAreaDTO newKnowledgeAreaDTO) {

        var validationErrors = knowledgeAreaValidator.isKACreationValid(newKnowledgeAreaDTO, id);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        KnowledgeArea newKnowledgeArea = knowledgeBodyCreator.createKnowledgeArea(newKnowledgeAreaDTO, id);

        return Response.ok(knowledgeAreaConverter.toDetailedKnowledgeAreaDTO(newKnowledgeArea)).build();
    }

}
