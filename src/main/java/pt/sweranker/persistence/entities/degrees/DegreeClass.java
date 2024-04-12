/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities.degrees;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Carlos Gonçalves
 */
@Entity
public class DegreeClass {

    @Id
    private Long id;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "ects", nullable = false)
    private Integer ects;

    @Column(name = "isoptional", nullable = false)
    private Boolean isOptional;

    @Column(name = "degree", nullable = false)
    private Course degree;
    //    @OneToMany
    //    @JoinColumn(name = "knowledgeareaid", referencedColumnName = "id")
    //    private Set<String> classTopics;

}
