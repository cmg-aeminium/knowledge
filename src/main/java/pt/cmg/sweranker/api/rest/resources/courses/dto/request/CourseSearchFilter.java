/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.courses.dto.request;

import javax.ws.rs.QueryParam;
import pt.cmg.sweranker.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
public class CourseSearchFilter {

    @QueryParam("university")
    public School university;

    @QueryParam("year")
    public Integer year;

    @QueryParam("name")
    public String name;

}
