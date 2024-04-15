/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities.degrees;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import pt.sweranker.persistence.entities.Language;

/**
 * @author Carlos Gonçalves
 */
public class CourseClassTopic {

    @Id
    @ManyToOne
    @JoinColumn(name = "degreeClassId", referencedColumnName = "id")
    private CourseClass degreeClass;

    @Id
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
}
