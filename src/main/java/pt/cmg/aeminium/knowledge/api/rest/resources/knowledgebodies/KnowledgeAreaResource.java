/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies;

import java.util.List;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import pt.cmg.aeminium.datamodel.knowledge.dao.knowledgeareas.KnowledgeAreaDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.knowledgeareas.KnowledgeAreaDAO.KnowledgeAreaFilterCriteria;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeArea;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeTopic;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converters.KnowledgeAreaConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converters.KnowledgeTopicConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.EditKnowledgeTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.SearchKnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.validators.KnowledgeAreaValidator;
import pt.cmg.aeminium.knowledge.tasks.knowledgebodies.KnowledgeBodyCreator;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
@Path("knowledgeareas")
@Tag(name = "Knowledge Areas", description = "Endpoints related operations with Knowledge Areas")
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

    @Inject
    private KnowledgeAreaValidator knowledgeAreaValidator;

    @Inject
    private KnowledgeBodyCreator knowledgeBodyCreator;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFiltered(@Valid @BeanParam SearchKnowledgeAreaDTO filter) {

        var validationErrors = knowledgeAreaValidator.isSearchValid(filter);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        List<KnowledgeArea> knowledgeAreas = knowledgeAreaDAO.findFiltered(
            new KnowledgeAreaFilterCriteria(filter.name,
                requestData.getSelectedLanguage(),
                filter.knowledgeBodyId,
                filter.size,
                filter.offset));

        return Response.ok(kaConverter.toKnowledgeAreaDTOs(knowledgeAreas)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKnowledgeArea(@PathParam("id") Long id) {
        KnowledgeArea ka = knowledgeAreaDAO.findById(id);

        if (ka == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorDTO(1, "Knowledge Area does not exist")).build();
        }

        return Response.ok(kaConverter.toDetailedKnowledgeAreaDTO(ka)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    @Transactional(value = TxType.REQUIRED)
    public Response createKnowledgeArea(@Valid CreateKnowledgeAreaDTO newKnowledgeAreaDTO) {

        var validationErrors = knowledgeAreaValidator.isKACreationValid(newKnowledgeAreaDTO);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        KnowledgeArea newKnowledgeArea = knowledgeBodyCreator.createKnowledgeArea(newKnowledgeAreaDTO);

        return Response.ok(kaConverter.toDetailedKnowledgeAreaDTO(newKnowledgeArea)).build();
    }

    @GET
    @Path("{id}/topics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopicsOfKnowledgeArea(@PathParam("id") Long knowledgeAreaId) {

        var knowledgeArea = knowledgeAreaDAO.findById(knowledgeAreaId);

        if (knowledgeArea == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorDTO(1, "Knowledge Area does not exist")).build();
        }

        return Response.ok(kaTopicConverter.toTopicDTOs(knowledgeArea.getKnowledgeTopics())).build();
    }

    @POST
    @Path("/{id}/topics")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    @Transactional(value = TxType.REQUIRED)
    public Response createKnowledgeTopics(@PathParam("id") Long id, @Valid CreateKnowledgeTopicDTO topicDTO) {

        var knowledgeArea = knowledgeAreaDAO.findById(id);
        if (knowledgeArea == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(1, "Knowledge Area does not exist")).build();
        }

        KnowledgeTopic newTopic = knowledgeBodyCreator.createTopic(topicDTO, id);

        return Response.ok(kaTopicConverter.toDetailedTopicDTO(newTopic)).build();
    }

    @PUT
    @Path("/{id}/topics/{topicId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    @Transactional(value = TxType.REQUIRED)
    public Response editCourseClassTopic(@PathParam("id") Long id, @PathParam("topicId") Long topicId, EditKnowledgeTopicDTO editTopicDTO) {

        var validationErrors = knowledgeAreaValidator.isTopicEditionValid(id, topicId, editTopicDTO);
        if (validationErrors.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(validationErrors.get()).build();
        }

        KnowledgeTopic editedTopic = knowledgeBodyCreator.editTopic(editTopicDTO, topicId);

        return Response.ok(kaTopicConverter.toDetailedTopicDTO(editedTopic)).build();
    }

    @DELETE
    @Path("/{id}/topics/{topicId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"GOD", "SCHOLAR"})
    @Transactional(value = TxType.REQUIRED)
    public Response deleteKnowledgeTopics(@PathParam("id") Long id, @PathParam("topicId") Long topicId) {
        knowledgeBodyCreator.deleteTopic(topicId);
        return Response.ok().build();
    }

}
