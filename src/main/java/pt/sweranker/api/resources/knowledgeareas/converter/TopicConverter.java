/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.api.resources.knowledgeareas.converter;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import pt.sweranker.api.resources.knowledgeareas.dto.response.TopicDTO;
import pt.sweranker.persistence.knowledgeareas.TopicTranslation;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class TopicConverter {

    public List<TopicDTO> toTopicDTOs(List<TopicTranslation> translatedTopics) {
        return translatedTopics.stream().map(this::toTopicDTO).collect(Collectors.toList());
    }

    public TopicDTO toTopicDTO(TopicTranslation translatedTopic) {

        TopicDTO dto = new TopicDTO();
        dto.id = translatedTopic.getTopic().getId();
        dto.name = translatedTopic.getName();
        dto.description = translatedTopic.getDescription();
        return dto;
    }

}
