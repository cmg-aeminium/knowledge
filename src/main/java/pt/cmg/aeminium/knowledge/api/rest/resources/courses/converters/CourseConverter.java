/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters;

import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.Course;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseDTO;
import pt.cmg.aeminium.knowledge.cache.TextTranslationCache;

/**
 * @author Carlos Gonçalves
 */
@Dependent
public class CourseConverter {

    @Inject
    private TextTranslationCache translationCache;

    @Inject
    private SchoolConverter schoolConverter;

    public List<CourseDTO> toCourseDTOs(List<Course> degrees) {
        return degrees.stream().map(this::toCourseDTO).toList();
    }

    public CourseDTO toCourseDTO(Course course) {
        return new CourseDTO(
            course.getId(),
            course.getAcronym(),
            schoolConverter.toSchoolDTO(course.getSchool()),
            translationCache.getTranslatedText(course.getNameTextContentId()),
            translationCache.getTranslatedText(course.getDescriptionContentId()),
            course.getYear(),
            course.getImage());

    }

}
