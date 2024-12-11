/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Carlos Gonçalves
 */
public record CourseClassDetailDTO(
    Long id,
    Integer year,
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
