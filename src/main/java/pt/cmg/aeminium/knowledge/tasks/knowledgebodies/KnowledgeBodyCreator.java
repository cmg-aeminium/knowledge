/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.tasks.knowledgebodies;

import java.util.ArrayList;
import java.util.List;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeBodyDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.CreateKnowledgeTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request.EditKnowledgeTopicDTO;
import pt.cmg.aeminium.knowledge.dao.identity.UserDAO;
import pt.cmg.aeminium.knowledge.dao.knowledgeareas.KnowledgeAreaDAO;
import pt.cmg.aeminium.knowledge.dao.knowledgeareas.KnowledgeBodyDAO;
import pt.cmg.aeminium.knowledge.dao.knowledgeareas.KnowledgeTopicDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeArea;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeBody;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeTopic;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.TextContent;
import pt.cmg.aeminium.knowledge.tasks.localisation.TranslationEditor;

/**
 * @author Carlos Gonçalves
 */
@Singleton
public class KnowledgeBodyCreator {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private TranslationEditor translationEditor;

    @Inject
    private UserDAO userDAO;

    @Inject
    private KnowledgeBodyDAO kBodyDAO;

    @Inject
    private KnowledgeAreaDAO kAreaDAO;

    @Inject
    private KnowledgeTopicDAO kTopicDAO;

    public KnowledgeBody createKnowledgeBody(CreateKnowledgeBodyDTO newBodyDTO) {

        TextContent defaultBodyName = translationEditor.createTranslatedTexts(newBodyDTO.names);
        TextContent defaultBodyDescription = translationEditor.createTranslatedTexts(newBodyDTO.descriptions);

        KnowledgeBody newBodyOfKnowledge = new KnowledgeBody();

        newBodyOfKnowledge.setImage(newBodyDTO.image);
        newBodyOfKnowledge.setYear(newBodyDTO.year);
        newBodyOfKnowledge.setNameTextContentId(defaultBodyName.getId());
        newBodyOfKnowledge.setDescriptionContentId(defaultBodyDescription.getId());

        // cached call
        newBodyOfKnowledge.setCreatedBy(userDAO.findById(requestData.getUserId()));

        kBodyDAO.create(newBodyOfKnowledge, true);

        return newBodyOfKnowledge;
    }

    public KnowledgeArea createKnowledgeArea(CreateKnowledgeAreaDTO newKAreaDTO) {
        return createKnowledgeArea(newKAreaDTO, newKAreaDTO.knowledgeBody);
    }

    public KnowledgeArea createKnowledgeArea(CreateKnowledgeAreaDTO newKAreaDTO, Long knowledgeBodyId) {

        KnowledgeArea newKnowledgeArea = new KnowledgeArea();
        KnowledgeBody bodyOfKnowledge = kBodyDAO.findById(knowledgeBodyId);

        newKnowledgeArea.setImage(newKAreaDTO.image);
        newKnowledgeArea.setBodyOfKnowledge(bodyOfKnowledge);

        TextContent defaultClassName = translationEditor.createTranslatedTexts(newKAreaDTO.names);
        TextContent defaultClassDescription = translationEditor.createTranslatedTexts(newKAreaDTO.descriptions);
        newKnowledgeArea.setNameTextContentId(defaultClassName.getId());
        newKnowledgeArea.setDescriptionContentId(defaultClassDescription.getId());

        kAreaDAO.create(newKnowledgeArea, true);

        bodyOfKnowledge.addKnowledgeArea(newKnowledgeArea);

        if (newKAreaDTO.topics != null && !newKAreaDTO.topics.isEmpty()) {

            List<KnowledgeTopic> topics = new ArrayList<>();
            for (var topicDTO : newKAreaDTO.topics) {

                TextContent defaultTopicName = translationEditor.createTranslatedTexts(topicDTO.names);
                TextContent defaultTopicDescription = translationEditor.createTranslatedTexts(topicDTO.descriptions);

                KnowledgeTopic topic = new KnowledgeTopic();

                topic.setKnowledgeArea(newKnowledgeArea);
                topic.setNameTextContentId(defaultTopicName.getId());
                topic.setDescriptionContentId(defaultTopicDescription.getId());

                kTopicDAO.create(topic, true);

                topics.add(topic);
            }

            newKnowledgeArea.setKnowledgeTopics(topics);
        }

        return newKnowledgeArea;

    }

    public KnowledgeTopic createTopic(CreateKnowledgeTopicDTO newTopicDTO, Long kAreaId) {

        KnowledgeTopic newTopic = new KnowledgeTopic();

        KnowledgeArea kArea = kAreaDAO.findById(kAreaId);

        newTopic.setKnowledgeArea(kArea);

        TextContent defaultTopicName = translationEditor.createTranslatedTexts(newTopicDTO.names);
        TextContent defaultTopicDescription = translationEditor.createTranslatedTexts(newTopicDTO.descriptions);
        newTopic.setNameTextContentId(defaultTopicName.getId());
        newTopic.setDescriptionContentId(defaultTopicDescription.getId());

        kTopicDAO.create(newTopic, true);

        kArea.addTopic(newTopic);

        return newTopic;
    }

    public KnowledgeTopic editTopic(EditKnowledgeTopicDTO newTopicDTO, Long topicId) {

        KnowledgeTopic topicToEdit = kTopicDAO.findById(topicId);

        if (newTopicDTO.names != null && !newTopicDTO.names.isEmpty()) {
            translationEditor.updateTraslatedTexts(topicToEdit.getNameTextContentId(), newTopicDTO.names);
        }

        if (newTopicDTO.descriptions != null && !newTopicDTO.descriptions.isEmpty()) {
            translationEditor.updateTraslatedTexts(topicToEdit.getDescriptionContentId(), newTopicDTO.descriptions);
        }

        return topicToEdit;
    }

    public void deleteTopic(Long topicId) {
        KnowledgeTopic topicToDelete = kTopicDAO.findById(topicId);
        KnowledgeArea kArea = topicToDelete.getKnowledgeArea();

        if (topicToDelete != null) {
            kTopicDAO.remove(topicToDelete);
            kArea.removeTopic(topicToDelete);
        }
    }

}
