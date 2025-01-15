/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author Carlos Gonçalves
 */
@JsonbPropertyOrder({"id", "name", "year", "semester", "ects", "isOptional", "createdAt", "description", "course"})
@Schema(description = "Represents a Course Class", example = """
    {
        "id": 1,
        "name": "Mathematical Analysis",
        "year": 1,
        "semester": 2,
        "ects": 5,
        "isOptional": false,
        "createdAt": 128976547311,
        "descripiton": "This class is...",
        "course" : {
            "id": 3,
            "name": "Software Engineering"
        }
    }
    """)
public record CourseClassDTO(
    Long id,
    Integer year,
    Integer semester,
    String name,
    CourseDTO course,
    String description,
    Double ects,
    boolean isOptional,
    LocalDateTime createdAt) {

    @JsonbPropertyOrder({"id", "name"})
    public record CourseDTO(
        Long id,
        String name) {
    }
}
