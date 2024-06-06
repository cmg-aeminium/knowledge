/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeAreaDetailDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeAreaDetailDTO.KATopicDTO;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeArea;

/**
 * @author Carlos Manuel
 */
@RequestScoped
public class KnowledgeAreaConverter {

    @Inject
    private HazelcastCache translationCache;

    public KnowledgeAreaDetailDTO toDetailedKnowledgeAreaDTO(KnowledgeArea knowledgeArea) {

        KnowledgeAreaDetailDTO dto = new KnowledgeAreaDetailDTO();

        dto.id = knowledgeArea.getId();
        dto.image = knowledgeArea.getImage();
        dto.name = translationCache.getTranslatedText(knowledgeArea.getNameTextContentId());
        dto.description = translationCache.getTranslatedText(knowledgeArea.getDescriptionContentId());

        dto.topics = new ArrayList<>();
        for (var topic : knowledgeArea.getKnowledgeTopics()) {
            KATopicDTO topicDTO = new KATopicDTO();
            topicDTO.id = topic.getId();
            topicDTO.name = translationCache.getTranslatedText(topic.getNameTextContentId());

            dto.topics.add(topicDTO);
        }

        return dto;
    }

    public List<KnowledgeAreaDTO> toKnowledgeAreaDTOs(List<KnowledgeArea> kas) {
        return kas.stream().map(this::toKnowledgeAreaDTO).collect(Collectors.toList());
    }

    public KnowledgeAreaDTO toKnowledgeAreaDTO(KnowledgeArea knowledgeArea) {

        KnowledgeAreaDTO dto = new KnowledgeAreaDTO();

        dto.id = knowledgeArea.getId();
        dto.image = knowledgeArea.getImage();
        dto.name = translationCache.getTranslatedText(knowledgeArea.getNameTextContentId());

        return dto;
    }

}
