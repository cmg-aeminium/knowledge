/*
 * Copyright (c) 2019
 * Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * All rights reserved.
 */
package pt.sweranker.persistence.entities.localisation;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import pt.sweranker.persistence.entities.Language;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "topics")
public class KnowledgeAreaId implements Serializable {

    private static final long serialVersionUID = 7936917422841373587L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "description")
    private String description;

    public KnowledgeAreaId() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
