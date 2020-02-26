/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.api.resources.knowledgeareas.converter;

import java.time.LocalDateTime;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import pt.sweranker.api.resources.knowledgeareas.dto.response.DetailedKnowledgeAreaDTO;
import pt.sweranker.persistence.knowledgeareas.KnowledgeAreaTranslation;

/**
 * @author Carlos Manuel
 */
@RequestScoped
public class KnowledgeAreaConverter {

    @Transactional(value = TxType.SUPPORTS)
    public DetailedKnowledgeAreaDTO toDetailedKnowledgeAreaDTO(KnowledgeAreaTranslation knowledgeArea) {

        DetailedKnowledgeAreaDTO dto = new DetailedKnowledgeAreaDTO();

        dto.id = knowledgeArea.getId();
        dto.image = knowledgeArea.getKnowledgeArea().getImage();
        dto.name = knowledgeArea.getTranslatedName();
        dto.description = knowledgeArea.getTranslatedDescription();
        dto.now = LocalDateTime.now();

        return dto;
    }

}
