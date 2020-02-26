package pt.sweranker.api.resources.knowledgeareas.dto.response;

import java.time.LocalDateTime;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;

@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class DetailedKnowledgeAreaDTO {

    public Long id;
    public String image;
    public String name;
    public String description;
    public LocalDateTime now;
}
