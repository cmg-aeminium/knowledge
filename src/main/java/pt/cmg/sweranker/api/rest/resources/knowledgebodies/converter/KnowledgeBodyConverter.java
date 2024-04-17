/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.knowledgebodies.converter;

import java.util.List;
import java.util.stream.Collectors;
import pt.cmg.sweranker.api.rest.resources.knowledgebodies.dto.response.KnowledgeBodyDTO;
import pt.cmg.sweranker.persistence.entities.knowledgebodies.KnowledgeBody;

/**
 * @author Carlos Gonçalves
 */
public class KnowledgeBodyConverter {

    public static List<KnowledgeBodyDTO> toKnowledgeBodyDTOs(List<KnowledgeBody> knowledgeBodies) {
        return knowledgeBodies.stream().map(KnowledgeBodyConverter::toKnowledgeBodyDTO).collect(Collectors.toList());
    }

    public static KnowledgeBodyDTO toKnowledgeBodyDTO(KnowledgeBody knowledgeBody) {
        KnowledgeBodyDTO dto = new KnowledgeBodyDTO();
        dto.id = knowledgeBody.getId();
        dto.year = knowledgeBody.getYear();
        dto.image = knowledgeBody.getImage();
        dto.name = knowledgeBody.getName();
        dto.description = knowledgeBody.getDescription();
        return dto;
    }

}
