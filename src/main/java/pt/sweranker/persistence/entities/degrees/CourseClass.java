/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities.degrees;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import pt.sweranker.persistence.entities.localisation.TextContent;

/**
 * @author Carlos Gonçalves
 */
@Entity
@Table(name = "courseclasses")
public class CourseClass {

    @Id
    private Long id;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "ects", nullable = false)
    private Integer ects;

    @Column(name = "isoptional", nullable = false)
    private boolean isOptional;

    @Column(name = "course", nullable = false)
    private Course course;

    @Column(name = "name")
    private TextContent nameContent;

    @Column(name = "description")
    private String description;

    @Transient
    private String name;

    public CourseClass() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getEcts() {
        return ects;
    }

    public void setEcts(Integer ects) {
        this.ects = ects;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean isOptional) {
        this.isOptional = isOptional;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public TextContent getNameContent() {
        return nameContent;
    }

    public void setNameContent(TextContent nameContent) {
        this.nameContent = nameContent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}