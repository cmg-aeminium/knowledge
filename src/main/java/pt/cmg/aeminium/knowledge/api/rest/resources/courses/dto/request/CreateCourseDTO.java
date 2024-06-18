/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request;

import java.util.HashSet;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class CreateCourseDTO {

    @NotNull(message = "1001 - Acronym cannot be null")
    public String acronym;

    @NotNull(message = "1002 - Image cannot be null")
    public String image;

    @NotNull(message = "1003 - School cannot be null")
    public Long school;

    @NotNull(message = "1004 - Year cannot be null")
    public Integer year;

    @NotEmpty(message = "1005 - No names were written")
    public HashSet<LocalisedTextDTO> names;

    @NotEmpty(message = "1006 - No descriptions were written")
    public HashSet<LocalisedTextDTO> descriptions;

}
