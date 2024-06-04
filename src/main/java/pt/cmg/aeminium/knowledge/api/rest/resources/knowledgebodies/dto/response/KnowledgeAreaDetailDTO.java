/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.response;

import java.util.List;

/**
 * @author Carlos Gonçalves
 */
public class KnowledgeAreaDetailDTO {

    public Long id;
    public String image;
    public String name;
    public String description;

    public List<KATopicDTO> topics;

    public static class KATopicDTO {
        public Long id;
        public String name;
    }
}
