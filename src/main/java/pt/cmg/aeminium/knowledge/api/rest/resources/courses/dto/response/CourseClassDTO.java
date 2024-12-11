/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;

/**
 * @author Carlos Gonçalves
 */
public record CourseClassDTO(
    Long id,
    Integer year,
    String name,
    String description,
    Double ects,
    boolean isOptional,
    LocalDateTime createdAt) {
}
