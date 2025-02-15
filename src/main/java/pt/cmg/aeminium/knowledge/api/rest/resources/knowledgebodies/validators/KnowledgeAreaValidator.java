/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.validators;

import java.util.List;
import java.util.Optional;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.knowledge.dao.knowledgeareas.KnowledgeBodyDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.knowledgeareas.KnowledgeTopicDAO;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeBody;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeTopic;
import pt.cmg.aeminium.datamodel.users.dao.identity.UserDAO;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.EditKnowledgeTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.SearchKnowledgeAreaDTO;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@Dependent
public class KnowledgeAreaValidator {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private KnowledgeBodyDAO knowledgeBodyDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private KnowledgeTopicDAO kTopicDAO;

    public Optional<List<ErrorDTO>> isKACreationValid(CreateKnowledgeAreaDTO kAreaDTO) {
        return isKACreationValid(kAreaDTO, kAreaDTO.knowledgeBody);
    }

    public Optional<List<ErrorDTO>> isKACreationValid(CreateKnowledgeAreaDTO kAreaDTO, Long knowledgeBodyId) {

        if (knowledgeBodyId == null) {
            return Optional.of(List.of(new ErrorDTO(1, "Knowledge Body cannot be null")));
        }

        KnowledgeBody knowledgeBody = knowledgeBodyDAO.findById(knowledgeBodyId);
        if (knowledgeBody == null) {
            return Optional.of(List.of(new ErrorDTO(2, "Knowledge Body does not exist")));
        }

        if (userDAO.findById(requestData.getUserId()) != knowledgeBody.getCreatedBy()) {
            return Optional.of(List.of(new ErrorDTO(3, "Knowledge Area creator is not the same as the Knowledge Body creator")));
        }

        return Optional.empty();
    }

    public Optional<List<ErrorDTO>> isTopicEditionValid(Long classId, Long topicId, EditKnowledgeTopicDTO topicEditionDTO) {

        KnowledgeTopic kaTopic = kTopicDAO.findById(topicId);
        if (kaTopic == null) {
            return Optional.of(List.of(new ErrorDTO(1, "Knowledge Topic does not exist")));
        }

        if (!kaTopic.getKnowledgeArea().getId().equals(classId)) {
            return Optional.of(List.of(new ErrorDTO(2, "Knowledge Topic does not belong to given Knowledge Area")));
        }

        return Optional.empty();
    }

    public Optional<List<ErrorDTO>> isSearchValid(SearchKnowledgeAreaDTO filter) {

        if (filter.knowledgeBodyId != null) {
            KnowledgeBody knowledgeBody = knowledgeBodyDAO.findById(filter.knowledgeBodyId);
            if (knowledgeBody == null) {
                return Optional.of(List.of(new ErrorDTO(1, "Knowledge Body with id " + filter.knowledgeBodyId + " does not exist")));
            }
        }

        return Optional.empty();
    }

}
