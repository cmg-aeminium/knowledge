/*
 * Copyright (c) 2019 
 * 
 * Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 *
 * All rights reserved.
 */
package pt.sweranker.cmg.persistence.knowledgeareas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import pt.sweranker.cmg.persistence.Language;

/**
 * @author Carlos Gonçalves
 */
@Entity(name = "KnowledgeAreaTranslations")
@IdClass(KnowledgeAreaId.class)
public class KnowledgeAreaTranslation {

    @Id
    @ManyToOne
    @JoinColumn(name = "knowledgeAreaId", referencedColumnName = "id")
    private KnowledgeArea knowledgeArea;

    @Id
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "translatedName", length = 300)
    private String translatedName;

    @Column(name = "translatedDescription", length = 2000)
    private String translatedDescription;

    public KnowledgeArea getKnowledgeArea() {
        return knowledgeArea;
    }

    public void setKnowledgeArea(KnowledgeArea knowledgeArea) {
        this.knowledgeArea = knowledgeArea;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getTranslatedName() {
        return translatedName;
    }

    public void setTranslatedName(String translatedName) {
        this.translatedName = translatedName;
    }

    public String getTranslatedDescription() {
        return translatedDescription;
    }

    public void setTranslatedDescription(String translatedDescription) {
        this.translatedDescription = translatedDescription;
    }

}
