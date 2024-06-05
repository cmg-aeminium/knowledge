/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request;

import java.util.HashSet;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class CreateKnowledgeBodyDTO {

    @NotNull(message = "1001 - Image cannot be null")
    public String image;

    @NotNull(message = "1002-Year cannot be null")
    public Integer year;

    @NotEmpty(message = "1003-No names were written")
    public HashSet<LocalisedTextDTO> names;

    @NotEmpty(message = "1004-No descriptions were written")
    public HashSet<LocalisedTextDTO> descriptions;
}
