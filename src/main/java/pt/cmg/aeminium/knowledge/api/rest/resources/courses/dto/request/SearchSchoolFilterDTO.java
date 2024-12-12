/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request;

import java.util.Set;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

/**
 * @author Carlos Gonçalves
 */
public class SearchSchoolFilterDTO {

    @QueryParam("country")
    public Set<@NotNull(message = "1001 - Country cannot be null") Long> countryIds;

    @QueryParam("size")
    @DefaultValue("30")
    public Long size;

    @QueryParam("offset")
    @DefaultValue("0")
    public Long offset;

}
