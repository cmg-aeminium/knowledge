/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CourseSearchFilter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseDTO;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.dao.schools.CourseDAO.CourseFilterCriteria;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.Course;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class CourseConverter {

    @Inject
    private HazelcastCache translationCache;

    @Inject
    private SchoolDAO schoolDAO;

    @Inject
    private SchoolConverter schoolConverter;

    public CourseFilterCriteria toDegreeFilterCriteria(CourseSearchFilter searchFilter) {
        School school = schoolDAO.findById(searchFilter.school);
        return new CourseFilterCriteria(school, searchFilter.year, searchFilter.name, searchFilter.acronym);
    }

    public List<CourseDTO> toCourseDTOs(List<Course> degrees) {
        return degrees.stream().map(this::toCourseDTO).collect(Collectors.toList());
    }

    public CourseDTO toCourseDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.id = course.getId();
        dto.acronym = course.getAcronym();
        dto.name = translationCache.getTranslatedText(course.getNameTextContentId());
        dto.description = translationCache.getTranslatedText(course.getDescriptionContentId());
        dto.image = course.getImage();
        dto.school = schoolConverter.toSchoolDTO(course.getSchool());
        dto.year = course.getYear();

        return dto;
    }

}
