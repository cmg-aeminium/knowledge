/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author Carlos Gonçalves
 */
@JsonbPropertyOrder({"id", "name", "year", "semester", "ects"})
public record CourseClassDTO(
    Long id,
    Integer year,
    Integer semester,
    String name,
    Double ects) {
}
