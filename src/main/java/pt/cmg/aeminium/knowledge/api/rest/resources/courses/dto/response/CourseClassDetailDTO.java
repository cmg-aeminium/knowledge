/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Carlos Gonçalves
 */
public class CourseClassDetailDTO {
    public Long id;
    public Integer year;
    public String name;
    public String description;
    public Double ects;
    public boolean isOptional;
    public LocalDateTime createdAt;
    public List<CourseClassTopicDTO> topics;
    public MinimalCourseDTO course;

    public static class CourseClassTopicDTO {
        public Long id;
        public String description;
        public Integer order;
    }
    public static class MinimalCourseDTO {
        public Long id;
        public String name;
    }

}
