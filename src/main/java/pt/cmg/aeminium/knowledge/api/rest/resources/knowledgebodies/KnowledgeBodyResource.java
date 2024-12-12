package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies;

import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.cmg.aeminium.datamodel.knowledge.dao.knowledgeareas.KnowledgeBodyDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.knowledgeareas.KnowledgeBodyDAO.KnowledgeBodyFilterCriteria;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeArea;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeBody;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converters.KnowledgeAreaConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converters.KnowledgeBodyConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeBodyDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.SearchKnowledgeBodyDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.validators.KnowledgeAreaValidator;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.validators.KnowledgeBodyValidator;
import pt.cmg.aeminium.knowledge.tasks.knowledgebodies.KnowledgeBodyCreator;
import pt.cmg.jakartautils.errors.ErrorDTO;

@Path("knowledgebodies")
@RequestScoped
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

    @Inject
    private KnowledgeBodyValidator knowledgeBodyValidator;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Valid @BeanParam SearchKnowledgeBodyDTO filter) {

        var validationErrors = knowledgeBodyValidator.isSearchValid(filter);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        List<KnowledgeBody> knowledgeBodies = knowledgeBodyDAO
            .findFiltered(new KnowledgeBodyFilterCriteria(filter.year,
                filter.name,
                requestData.getSelectedLanguage(),
                filter.createdBy,
                filter.size,
                filter.offset));

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
    @Transactional(value = TxType.REQUIRED)
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
    @Transactional(value = TxType.REQUIRED)
    public Response createKnowledgeArea(@PathParam("id") Long id, @Valid CreateKnowledgeAreaDTO newKnowledgeAreaDTO) {

        var validationErrors = knowledgeAreaValidator.isKACreationValid(newKnowledgeAreaDTO, id);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        KnowledgeArea newKnowledgeArea = knowledgeBodyCreator.createKnowledgeArea(newKnowledgeAreaDTO, id);

        return Response.ok(knowledgeAreaConverter.toDetailedKnowledgeAreaDTO(newKnowledgeArea)).build();
    }

}
