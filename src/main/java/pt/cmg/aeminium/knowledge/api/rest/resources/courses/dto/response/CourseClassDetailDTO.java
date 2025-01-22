/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author Carlos Gonçalves
 */
@JsonbPropertyOrder({"id", "name", "year", "semester", "ects", "isOptional", "createdAt", "description", "topics"})
public record CourseClassDetailDTO(
    Long id,
    Integer year,
    Integer semester,
    String name,
    String description,
    Double ects,
    boolean isOptional,
    LocalDateTime createdAt,
    List<ClassTopicDTO> topics) {

    public record ClassTopicDTO(
        Long id,
        String description,
        Integer order) {
    }
}
