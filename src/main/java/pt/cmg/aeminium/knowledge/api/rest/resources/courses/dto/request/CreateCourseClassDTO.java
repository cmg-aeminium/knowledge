/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request;

import java.util.HashSet;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class CreateCourseClassDTO {

    @NotNull(message = "1001 - Year cannot be null")
    public Integer year;

    public Integer semester;

    public Double ects;

    // NOTE: implicitly not null and default value = false as per JDK
    public boolean isOptional;

    @NotEmpty(message = "1002 - No names were written")
    public HashSet<LocalisedTextDTO> names;

    @NotEmpty(message = "1003 - No descriptions were written")
    public HashSet<LocalisedTextDTO> descriptions;

    public List<CreateClassTopicDTO> topics;

    public Long course;

    public static class CreateClassTopicDTO {

        @NotNull(message = "1004 - Order cannot be null")
        public Integer order;

        @NotEmpty(message = "1005 - No topic descriptions were written")
        public HashSet<LocalisedTextDTO> descriptions;
    }

}
