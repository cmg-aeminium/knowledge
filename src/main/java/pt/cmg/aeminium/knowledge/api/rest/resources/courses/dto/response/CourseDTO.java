/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

/**
 * @author Carlos Gonçalves
 */
public class CourseDTO {
    public Long id;
    public String acronym;
    public SchoolDTO school;
    public String name;
    public String description;
    public Integer year;
    public String image;
}
