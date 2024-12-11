/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeTopic;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeTopicDetailDTO;
import pt.cmg.aeminium.knowledge.cache.TextTranslationCache;

/**
 * @author Carlos Gonçalves
 */
@Dependent
public class KnowledgeTopicConverter {

    @Inject
    private TextTranslationCache translationCache;

    public List<KnowledgeTopicDetailDTO> toDetailedTopicDTOs(List<KnowledgeTopic> topics) {
        return topics.stream().map(this::toDetailedTopicDTO).collect(Collectors.toList());
    }

    public KnowledgeTopicDetailDTO toDetailedTopicDTO(KnowledgeTopic topic) {
        return new KnowledgeTopicDetailDTO(
            topic.getId(),
            translationCache.getTranslatedText(topic.getNameTextContentId()),
            translationCache.getTranslatedText(topic.getDescriptionContentId()));
    }

    public List<KnowledgeTopicDTO> toTopicDTOs(List<KnowledgeTopic> topics) {
        return topics.stream().map(this::toTopicDTO).collect(Collectors.toList());
    }

    public KnowledgeTopicDTO toTopicDTO(KnowledgeTopic topic) {
        return new KnowledgeTopicDTO(
            topic.getId(),
            translationCache.getTranslatedText(topic.getNameTextContentId()));
    }

}
