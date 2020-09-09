/*
 * Copyright (c) 2019 
 * 
 * Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 *
 * All rights reserved.
 */
package pt.sweranker.persistence.entities.knowledgeareas;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import pt.sweranker.persistence.entities.Language;

/**
 * @author Carlos Gonçalves
 */
public class KnowledgeAreaId implements Serializable {

    private static final long serialVersionUID = 7936917422841373587L;

    @Column(name = "knowledgeArea", nullable = false)
    private Long knowledgeArea;

    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Override
    public int hashCode() {
        return Objects.hash(knowledgeArea, language);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KnowledgeAreaId other = (KnowledgeAreaId) obj;
        return Objects.equals(knowledgeArea, other.knowledgeArea) && Objects.equals(language, other.language);
    }

}
