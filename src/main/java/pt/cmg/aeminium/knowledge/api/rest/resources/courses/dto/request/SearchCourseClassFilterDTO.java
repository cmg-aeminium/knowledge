/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

/**
 * @author Carlos Gonçalves
 */
public class SearchCourseClassFilterDTO {

    @QueryParam("year")
    public Integer year;

    @QueryParam("semester")
    public Integer semester;

    @QueryParam("name")
    public String name;

    @QueryParam("isOptional")
    public Boolean isOptional;

    @QueryParam("course")
    public Long course;

    @QueryParam("school")
    public Long school;

    @QueryParam("size")
    @DefaultValue("30")
    public Long size;

    @QueryParam("offset")
    @DefaultValue("0")
    public Long offset;

}
