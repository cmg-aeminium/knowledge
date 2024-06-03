/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request;

import java.util.HashSet;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class CreateCourseClassDTO {

    @NotNull(message = "Year cannot be null")
    public Integer year;

    public Integer semester;

    public Double ects;

    // NOTE: implicitly not null and default value = false as per JDK
    public boolean isOptional;

    @NotEmpty(message = "No names were written")
    public HashSet<LocalisedTextDTO> names;

    @NotEmpty(message = "No descriptions were written")
    public HashSet<LocalisedTextDTO> descriptions;

    public List<CourseClassTopicDTO> topics;

    public static class CourseClassTopicDTO {

        @NotNull
        public Integer order;

        @NotEmpty(message = "No topic descriptions were written")
        public HashSet<LocalisedTextDTO> descriptions;
    }

}
