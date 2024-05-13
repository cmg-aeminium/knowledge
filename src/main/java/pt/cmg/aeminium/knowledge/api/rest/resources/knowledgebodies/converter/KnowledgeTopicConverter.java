/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeTopicDTO;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeTopic;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class KnowledgeTopicConverter {

    @Inject
    private HazelcastCache translationCache;

    public List<KnowledgeTopicDTO> toTopicDTOs(List<KnowledgeTopic> topics) {
        return topics.stream().map(this::toTopicDTO).collect(Collectors.toList());
    }

    public KnowledgeTopicDTO toTopicDTO(KnowledgeTopic topic) {

        KnowledgeTopicDTO dto = new KnowledgeTopicDTO();
        dto.id = topic.getId();
        dto.name = translationCache.getTranslatedText(topic.getNameTextContentId());
        dto.description = translationCache.getTranslatedText(topic.getDescriptionContentId());
        return dto;
    }

}
