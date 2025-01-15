/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author Carlos Gonçalves
 */
@JsonbPropertyOrder({"id", "name", "year", "semester", "ects", "isOptional", "createdAt", "description", "topics"})
@Schema(description = "Represents a detailed view of a Course Class", example = """
    {
        "id": 1,
        "name": "Mathematical Analysis",
        "year": 1,
        "semester": 2,
        "ects": 5,
        "isOptional": false,
        "createdAt": 128976547311,
        "descripiton": "This class is...",
        "topics" : [
            {
                "id": 1,
                "description": "Functions",
                "order": 1
            },
            {
                "id": 4,
                "description": "Sets",
                "order": 2
            }
        ]
    }
    """)
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
