/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.api.resources.degrees.dto;

import pt.sweranker.persistence.entities.degrees.University;

/**
 * @author Carlos Gonçalves
 */
public class DegreeDTO {
    public Long id;
    public String acronym;
    public University university;
    public String name;
    public String description;
    public Integer year;
    public String image;
}
