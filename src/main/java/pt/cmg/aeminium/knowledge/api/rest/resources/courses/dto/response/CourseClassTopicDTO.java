/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author Carlos Gonçalves
 */
@JsonbPropertyOrder({"id", "description", "order"})
@Schema(description = "Represents a Course Class Topic", example = """
    {
        "id": 1028,
        "name": "Functions",
        "order": 2
    }
    """)
public record CourseClassTopicDTO(
    Long id,
    String description,
    Integer order) {
}
