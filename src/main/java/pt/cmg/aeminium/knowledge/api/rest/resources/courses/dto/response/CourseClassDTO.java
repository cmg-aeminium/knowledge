/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;

/**
 * @author Carlos Gonçalves
 */
public class CourseClassDTO {
    public Long id;
    public Integer year;
    public String name;
    public String description;
    public Double ects;
    public boolean isOptional;
    public LocalDateTime createdAt;
}
