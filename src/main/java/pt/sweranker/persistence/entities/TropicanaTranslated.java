/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import pt.sweranker.persistence.entities.localisation.TextContent;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "translations")
public class TropicanaTranslated implements Serializable {

    private static final long serialVersionUID = -7950524049133845822L;

    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "texts")
    @OneToOne
    private TextContent nameContent;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextContent getNameContent() {
        return nameContent;
    }

    public void setNameContent(TextContent nameContent) {
        this.nameContent = nameContent;
    }

}
