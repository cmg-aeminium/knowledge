/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converters;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.knowledge.entities.knowledgebodies.KnowledgeBody;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeBodyDTO;
import pt.cmg.aeminium.knowledge.cache.TextTranslationCache;

/**
 * @author Carlos Gonçalves
 */
@Dependent
public class KnowledgeBodyConverter {

    @Inject
    private TextTranslationCache translationCache;

    public List<KnowledgeBodyDTO> toKnowledgeBodyDTOs(List<KnowledgeBody> knowledgeBodies) {
        return knowledgeBodies.stream().map(this::toKnowledgeBodyDTO).collect(Collectors.toList());
    }

    public KnowledgeBodyDTO toKnowledgeBodyDTO(KnowledgeBody knowledgeBody) {
        return new KnowledgeBodyDTO(
            knowledgeBody.getId(),
            translationCache.getTranslatedText(knowledgeBody.getNameTextContentId()),
            translationCache.getTranslatedText(knowledgeBody.getDescriptionContentId()),
            knowledgeBody.getYear(),
            knowledgeBody.getImage());
    }

}
