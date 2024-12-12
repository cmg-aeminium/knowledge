/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

/**
 * @author Carlos Gonçalves
 */
public class SearchCourseFilterDTO {

    @QueryParam("school")
    public Long school;

    @QueryParam("year")
    public Integer year;

    @QueryParam("name")
    public String name;

    @QueryParam("acronym")
    public String acronym;

    @QueryParam("size")
    @DefaultValue("30")
    public Long size;

    @QueryParam("offset")
    @DefaultValue("0")
    public Long offset;

}
