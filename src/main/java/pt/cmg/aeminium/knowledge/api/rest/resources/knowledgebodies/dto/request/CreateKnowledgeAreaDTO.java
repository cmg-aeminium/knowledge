/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request;

import java.util.HashSet;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class CreateKnowledgeAreaDTO {

    @NotNull(message = "1001-Image cannot be null")
    public String image;

    public Long knowledgeBody;

    @NotEmpty(message = "1002-No names were written")
    public HashSet<LocalisedTextDTO> names;

    @NotEmpty(message = "1003-No descriptions were written")
    public HashSet<LocalisedTextDTO> descriptions;

    public List<@Valid CreateKATopicDTO> topics;

    public static class CreateKATopicDTO {

        @NotEmpty(message = "1004-No topic names were written")
        public HashSet<LocalisedTextDTO> names;

        @NotEmpty(message = "1005-No topic descriptions were written")
        public HashSet<LocalisedTextDTO> descriptions;
    }
}
