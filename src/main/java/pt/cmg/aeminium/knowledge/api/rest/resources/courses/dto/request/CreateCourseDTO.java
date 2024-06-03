/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request;

import java.util.HashSet;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class CreateCourseDTO {

    @NotNull(message = "Acronym cannot be null")
    public String acronym;

    @NotNull(message = "Image cannot be null")
    public String image;

    @NotNull(message = "School cannot be null")
    public Long school;

    @NotNull(message = "Year cannot be null")
    public Integer year;

    @NotEmpty(message = "No names were written")
    public HashSet<LocalisedTextDTO> names;

    @NotEmpty(message = "No names were written")
    public HashSet<LocalisedTextDTO> descriptions;

}
