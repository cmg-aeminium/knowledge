/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.courses.converters;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pt.cmg.sweranker.api.rest.filters.request.RequestContextData;
import pt.cmg.sweranker.api.rest.filters.request.RequestData;
import pt.cmg.sweranker.api.rest.resources.courses.dto.request.CourseSearchFilter;
import pt.cmg.sweranker.api.rest.resources.courses.dto.response.CourseDTO;
import pt.cmg.sweranker.dao.schools.CourseDAO.DegreeFilterCriteria;
import pt.cmg.sweranker.dao.schools.SchoolDAO;
import pt.cmg.sweranker.persistence.entities.schools.Course;
import pt.cmg.sweranker.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class CourseConverter {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private SchoolDAO schoolDAO;

    @Inject
    private SchoolConverter schoolConverter;

    public DegreeFilterCriteria toDegreeFilterCriteria(CourseSearchFilter searchFilter) {
        School school = schoolDAO.findById(searchFilter.school);
        return new DegreeFilterCriteria(school, searchFilter.year, searchFilter.name, searchFilter.acronym);
    }

    public List<CourseDTO> toCourseDTOs(List<Course> degrees) {
        return degrees.stream().map(this::toCourseDTO).collect(Collectors.toList());
    }

    public CourseDTO toCourseDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.id = course.getId();
        dto.acronym = course.getAcronym();
        dto.name = course.getName();
        dto.description = course.getDescription();
        dto.image = course.getImage();
        dto.school = schoolConverter.toSchoolDTO(course.getSchool());
        dto.year = course.getYear();

        return dto;
    }

}
