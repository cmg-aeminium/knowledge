/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities.schools;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "tropicana")
public class TropicanaTranslated implements Serializable {

    private static final long serialVersionUID = -7950524049133845822L;

    @Id
    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "cenas")
    private String cenas;

    @Column(name = "cenas")
    private String description;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCenas() {
        return cenas;
    }

    public void setCenas(String cenas) {
        this.cenas = cenas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
