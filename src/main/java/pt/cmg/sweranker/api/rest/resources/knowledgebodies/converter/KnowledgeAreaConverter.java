/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.knowledgebodies.converter;

import pt.cmg.sweranker.api.rest.resources.knowledgebodies.dto.response.DetailedKnowledgeAreaDTO;
import pt.cmg.sweranker.persistence.entities.knowledgebodies.KnowledgeArea;

/**
 * @author Carlos Manuel
 */
public class KnowledgeAreaConverter {

    public static DetailedKnowledgeAreaDTO toDetailedKnowledgeAreaDTO(KnowledgeArea knowledgeArea) {

        DetailedKnowledgeAreaDTO dto = new DetailedKnowledgeAreaDTO();

        dto.id = knowledgeArea.getId();
        dto.image = knowledgeArea.getImage();
        dto.name = knowledgeArea.getName();
        dto.description = knowledgeArea.getDescription();
        dto.bodyOfKnowledge = BodyOfKnowledgeConverter.toBodyOfKnowledgeDTO(knowledgeArea.getBodyOfKnowledge());

        return dto;
    }

}
