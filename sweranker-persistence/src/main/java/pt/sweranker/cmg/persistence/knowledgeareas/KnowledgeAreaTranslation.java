/*
 * Copyright (c) 2019 
 * 
 * Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 *
 * All rights reserved.
 */
package pt.sweranker.cmg.persistence.knowledgeareas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;
import pt.sweranker.cmg.persistence.Language;

/**
 * Knowledge Area Translation
 * 
 * @author Carlos Gonçalves
 */
@Entity(name = "KnowledgeAreaTranslations")
@IdClass(KnowledgeAreaId.class)
@NamedQuery(
    name = "KnowledgeAreaTranslations.findByIdAndLanguage",
    query = "SELECT kat FROM KnowledgeAreaTranslations kat INNER JOIN FETCH kat.knowledgeArea ka WHERE kat.knowledgeArea.id = :id AND kat.language = :language",
    hints = {
        @QueryHint(name = QueryHints.CACHE_USAGE, value = CacheUsage.CheckCacheThenDatabase),
        @QueryHint(name = QueryHints.QUERY_RESULTS_CACHE_SIZE, value = "2")
    })
public class KnowledgeAreaTranslation implements Serializable {

    private static final long serialVersionUID = -906898775787390090L;

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
