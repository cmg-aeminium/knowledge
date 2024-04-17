/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.knowledgebodies.converter;

import pt.cmg.sweranker.api.rest.resources.knowledgebodies.dto.response.BodyOfKnowledgeDTO;
import pt.cmg.sweranker.persistence.entities.knowledgebodies.KnowledgeBody;

/**
 * @author Carlos Gonçalves
 */
public class BodyOfKnowledgeConverter {

    public static BodyOfKnowledgeDTO toBodyOfKnowledgeDTO(KnowledgeBody bok) {
        BodyOfKnowledgeDTO dto = new BodyOfKnowledgeDTO();
        dto.id = bok.getId();
        dto.image = bok.getImage();
        dto.name = bok.getName();
        dto.description = bok.getDescription();
        dto.year = bok.getYear();

        return dto;
    }
}
