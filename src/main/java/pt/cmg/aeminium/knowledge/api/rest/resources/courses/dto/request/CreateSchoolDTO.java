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
 * @author Carlos Gonçaalves
 */
public class CreateSchoolDTO {

    @NotNull(message = "1001-Country cannot be null")
    public Long country;

    @NotEmpty(message = "1002-No names were written")
    public HashSet<LocalisedTextDTO> names;

}
