/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import pt.sweranker.persistence.entities.localisation.TextContent;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "translations")
public class TropicanaTranslationAgain {

    @Id
    private Long id;

    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "textcontent")
    @OneToOne
    private TextContent textContent;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TextContent getTextContent() {
        return textContent;
    }

    public void setTextContent(TextContent textContent) {
        this.textContent = textContent;
    }

    public void setName(String name) {
        this.name = name;
    }

}
