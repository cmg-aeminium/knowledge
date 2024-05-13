/*
 * Copyright (c) 2019
 * Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * All rights reserved.
 */
package pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;

@Entity
@Table(name = "knowledgeareas")
@Cache(type = CacheType.FULL, isolation = CacheIsolationType.SHARED, alwaysRefresh = true, coordinationType = CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES)
@NamedQuery(name = KnowledgeArea.QUERY_FIND_ALL, query = "SELECT ka FROM KnowledgeArea ka")
public class KnowledgeArea implements Serializable {

    private static final long serialVersionUID = 4341439068096536870L;

    public static final String QUERY_FIND_ALL = "KnowledgeArea.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "KNOWLEDGEAREA_SEQUENCE")
    @SequenceGenerator(name = "KNOWLEDGEAREA_SEQUENCE",
        sequenceName = "knowledgearea_id_seq",
        initialValue = 1,
        allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "name")
    private Long nameTextContentId;

    @Column(name = "description")
    private Long descriptionContentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "knowledgebody", referencedColumnName = "id")
    private KnowledgeBody knowledgeBody;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "knowledgeArea", fetch = FetchType.LAZY)
    private List<KnowledgeTopic> knowledgeTopics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getNameTextContentId() {
        return nameTextContentId;
    }

    public void setNameTextContentId(Long nameTextContentId) {
        this.nameTextContentId = nameTextContentId;
    }

    public Long getDescriptionContentId() {
        return descriptionContentId;
    }

    public void setDescriptionContentId(Long descriptionContentId) {
        this.descriptionContentId = descriptionContentId;
    }

    public KnowledgeBody getBodyOfKnowledge() {
        return knowledgeBody;
    }

    public void setBodyOfKnowledge(KnowledgeBody bodyOfKnowledge) {
        this.knowledgeBody = bodyOfKnowledge;
    }

    public List<KnowledgeTopic> getKnowledgeTopics() {
        return knowledgeTopics;
    }

    public void setKnowledgeTopics(List<KnowledgeTopic> knowledgeTopics) {
        this.knowledgeTopics = knowledgeTopics;
    }

}
