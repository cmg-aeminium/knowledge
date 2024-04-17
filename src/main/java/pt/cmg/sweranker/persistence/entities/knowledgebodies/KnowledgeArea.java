/*
 * Copyright (c) 2019
 * Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * All rights reserved.
 */
package pt.cmg.sweranker.persistence.entities.knowledgebodies;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import pt.cmg.sweranker.persistence.entities.localisation.TextContent;

@Entity
@Table(name = "knowledgeareas")
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

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "name", referencedColumnName = "id")
    private TextContent nameContent;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "description", referencedColumnName = "id")
    private TextContent descriptionContent;

    @Transient
    private String name;

    @Transient
    private String description;

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

    public TextContent getNameContent() {
        return nameContent;
    }

    public void setNameContent(TextContent nameContent) {
        this.nameContent = nameContent;
    }

    public TextContent getDescriptionContent() {
        return descriptionContent;
    }

    public void setDescriptionContent(TextContent descriptionContent) {
        this.descriptionContent = descriptionContent;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
