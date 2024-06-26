/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.config.PropertyOrderStrategy;

/**
 * @author Carlos Gonçalves
 */
@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class KnowledgeTopicDetailDTO {
    public Long id;
    public String name;
    public String description;
}
