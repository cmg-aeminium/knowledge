/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response;

import java.util.List;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author Carlos Gonçalves
 */
@JsonbPropertyOrder({"id", "name", "image", "description", "topics"})
public record KnowledgeAreaDetailDTO(
    Long id,
    String image,
    String name,
    String description,
    List<KATopicDTO> topics) {

    public record KATopicDTO(
        Long id,
        String name) {
    }
}
