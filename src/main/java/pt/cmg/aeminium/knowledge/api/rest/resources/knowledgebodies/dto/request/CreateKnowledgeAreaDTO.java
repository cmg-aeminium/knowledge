/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request;

import java.util.HashSet;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class CreateKnowledgeAreaDTO {

    @NotNull(message = "Image cannot be null")
    public String image;

    public Long knowledgeBody;

    @NotEmpty(message = "No names were written")
    public HashSet<LocalisedTextDTO> names;

    @NotEmpty(message = "No descriptions were written")
    public HashSet<LocalisedTextDTO> descriptions;

    public List<@Valid CreateKATopicDTO> topics;

    public static class CreateKATopicDTO {

        @NotEmpty(message = "No topic names were written")
        public HashSet<LocalisedTextDTO> names;

        @NotEmpty(message = "No topic descriptions were written")
        public HashSet<LocalisedTextDTO> descriptions;
    }
}
