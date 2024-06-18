/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request;

import java.util.HashSet;
import jakarta.validation.constraints.NotEmpty;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class CreateKnowledgeTopicDTO {
    @NotEmpty(message = "1001-No topic names were written")
    public HashSet<LocalisedTextDTO> names;

    @NotEmpty(message = "1002-No topic descriptions were written")
    public HashSet<LocalisedTextDTO> descriptions;
}
