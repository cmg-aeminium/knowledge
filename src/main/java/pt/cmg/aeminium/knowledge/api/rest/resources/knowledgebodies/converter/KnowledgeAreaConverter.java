/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeArea;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeAreaDetailDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeAreaDetailDTO.KATopicDTO;
import pt.cmg.aeminium.knowledge.cache.TextTranslationCache;

/**
 * @author Carlos Manuel
 */
@Dependent
public class KnowledgeAreaConverter {

    @Inject
    private TextTranslationCache translationCache;

    public KnowledgeAreaDetailDTO toDetailedKnowledgeAreaDTO(KnowledgeArea knowledgeArea) {

        List<KATopicDTO> topics = knowledgeArea.getKnowledgeTopics()
            .stream()
            .map(topic -> new KATopicDTO(
                topic.getId(),
                translationCache.getTranslatedText(topic.getNameTextContentId())))
            .toList();

        return new KnowledgeAreaDetailDTO(
            knowledgeArea.getId(),
            knowledgeArea.getImage(),
            translationCache.getTranslatedText(knowledgeArea.getNameTextContentId()),
            translationCache.getTranslatedText(knowledgeArea.getDescriptionContentId()),
            topics);
    }

    public List<KnowledgeAreaDTO> toKnowledgeAreaDTOs(List<KnowledgeArea> kas) {
        return kas.stream().map(this::toKnowledgeAreaDTO).collect(Collectors.toList());
    }

    public KnowledgeAreaDTO toKnowledgeAreaDTO(KnowledgeArea knowledgeArea) {

        return new KnowledgeAreaDTO(knowledgeArea.getId(),
            knowledgeArea.getImage(),
            translationCache.getTranslatedText(knowledgeArea.getNameTextContentId()));

    }

}
