/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

/**
 * @author Carlos Gonçalves
 */
public class SearchKnowledgeBodyDTO {

    @QueryParam("year")
    public Integer year;

    @QueryParam("createdBy")
    public Long createdBy;

    @QueryParam("name")
    public String name;

    @QueryParam("size")
    @DefaultValue("30")
    @Min(value = 0)
    public Long size;

    @QueryParam("offset")
    @DefaultValue("0")
    @Min(value = 0)
    public Long offset;

}
