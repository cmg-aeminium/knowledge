/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.knowledgebodies.converter;

import java.util.List;
import java.util.stream.Collectors;
import pt.cmg.sweranker.api.rest.resources.knowledgebodies.dto.response.KnowledgeTopicDTO;
import pt.cmg.sweranker.persistence.entities.knowledgebodies.KnowledgeTopic;

/**
 * @author Carlos Gonçalves
 */
public class KnowledgeTopicConverter {

    public static List<KnowledgeTopicDTO> toTopicDTOs(List<KnowledgeTopic> topics) {
        return topics.stream().map(KnowledgeTopicConverter::toTopicDTO).collect(Collectors.toList());
    }

    public static KnowledgeTopicDTO toTopicDTO(KnowledgeTopic topic) {

        KnowledgeTopicDTO dto = new KnowledgeTopicDTO();
        dto.id = topic.getId();
        dto.name = topic.getName();
        dto.description = topic.getDescription();
        return dto;
    }

}
