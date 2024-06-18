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
public class CreateCourseClassTopicDTO {
    @NotNull(message = "1001 - No order given to topic")
    public Integer order;

    @NotEmpty(message = "1002 - No topic descriptions were written")
    public HashSet<LocalisedTextDTO> descriptions;
}
