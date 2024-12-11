/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

/**
 * @author Carlos Gonçalves
 */
public record CourseClassTopicDTO(
    Long id,
    String description,
    Integer order) {
}
