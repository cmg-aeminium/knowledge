/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author Carlos Gonçalves
 */
@JsonbPropertyOrder({"id", "name", "acronym", "year"})
public record CourseDTO(
    Long id,
    String acronym,
    String name,
    Integer year) {
}
