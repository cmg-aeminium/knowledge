/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.knowledgeareas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import pt.sweranker.persistence.Language;

/**
 * @author Carlos Gonçalves
 */
@Entity(name = "TopicTranslations")
@IdClass(TopicId.class)
public class TopicTranslation {

    @Id
    @JoinColumn(name = "topicId", referencedColumnName = "id")
    private Topic topic;

    @Id
    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

}
