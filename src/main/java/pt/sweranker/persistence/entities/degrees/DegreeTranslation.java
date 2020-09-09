/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities.degrees;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import pt.sweranker.persistence.entities.Language;

/**
 * @author Carlos Gonçalves
 */
@Entity(name = "DegreeTranslations")
@IdClass(value = DegreeId.class)
public class DegreeTranslation implements Serializable {

    /**
     * Auto-generated by IDE
     */
    private static final long serialVersionUID = 1995101805194981416L;

    @Id
    @ManyToOne
    @JoinColumn(name = "degreeId", referencedColumnName = "id")
    private Degree degree;

    @Id
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

}
