/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.converter;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.DetailedKnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response.KnowledgeAreaDTO;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeArea;

/**
 * @author Carlos Manuel
 */
@RequestScoped
public class KnowledgeAreaConverter {

    @Inject
    private HazelcastCache translationCache;

    @Inject
    private KnowledgeBodyConverter knowledgeBodyConverter;

    public DetailedKnowledgeAreaDTO toDetailedKnowledgeAreaDTO(KnowledgeArea knowledgeArea) {

        DetailedKnowledgeAreaDTO dto = new DetailedKnowledgeAreaDTO();

        dto.id = knowledgeArea.getId();
        dto.image = knowledgeArea.getImage();
        dto.name = translationCache.getTranslatedText(knowledgeArea.getNameTextContentId());
        dto.description = translationCache.getTranslatedText(knowledgeArea.getDescriptionContentId());
        dto.bodyOfKnowledge = knowledgeBodyConverter.toKnowledgeBodyDTO(knowledgeArea.getBodyOfKnowledge());

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
