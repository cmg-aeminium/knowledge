/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CourseSearchFilter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassDetailDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassDetailDTO.ClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassDetailDTO.MinimalCourseDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseDTO;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.dao.schools.CourseDAO.CourseFilterCriteria;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.Course;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClass;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClassTopic;
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

    public List<CourseClassDTO> toCourseClassesDTO(List<CourseClass> classes) {
        return classes.stream().map(this::toCourseClassesDTO).collect(Collectors.toList());
    }

    public CourseClassDTO toCourseClassesDTO(CourseClass courseClass) {
        CourseClassDTO dto = new CourseClassDTO();

        dto.id = courseClass.getId();
        dto.year = courseClass.getYear();
        dto.name = translationCache.getTranslatedText(courseClass.getNameTextContentId());
        dto.description = translationCache.getTranslatedText(courseClass.getDescriptionContentId());
        dto.ects = courseClass.getEcts();
        dto.isOptional = courseClass.isOptional();
        dto.createdAt = courseClass.getCreatedAt();

        return dto;
    }

    public List<CourseClassTopicDTO> toCourseClasseTopicDTOs(List<CourseClassTopic> topics) {
        return topics.stream().map(this::toCourseClasseTopicDTO).collect(Collectors.toList());
    }

    public CourseClassTopicDTO toCourseClasseTopicDTO(CourseClassTopic topic) {

        if (topic == null) {
            return null;
        }

        CourseClassTopicDTO dto = new CourseClassTopicDTO();

        dto.id = topic.getId();
        dto.description = translationCache.getTranslatedText(topic.getDescriptionContentId());
        dto.order = topic.getOrder();

        return dto;
    }

    public CourseClassDetailDTO toCourseClassDTO(CourseClass courseClass) {
        CourseClassDetailDTO dto = new CourseClassDetailDTO();

        dto.id = courseClass.getId();
        dto.year = courseClass.getYear();
        dto.name = translationCache.getTranslatedText(courseClass.getNameTextContentId());
        dto.description = translationCache.getTranslatedText(courseClass.getDescriptionContentId());
        dto.ects = courseClass.getEcts();
        dto.isOptional = courseClass.isOptional();
        dto.createdAt = courseClass.getCreatedAt();

        MinimalCourseDTO courseDTO = new MinimalCourseDTO();
        courseDTO.id = courseClass.getCourse().getId();
        courseDTO.name = translationCache.getTranslatedText(courseClass.getCourse().getNameTextContentId());

        dto.course = courseDTO;

        List<ClassTopicDTO> topics = new ArrayList<>();
        for (var topic : courseClass.getCourseClassTopics()) {

            ClassTopicDTO topicDTO = new ClassTopicDTO();
            topicDTO.id = topic.getId();
            topicDTO.description = translationCache.getTranslatedText(topic.getDescriptionContentId());
            topicDTO.order = topic.getOrder();

            topics.add(topicDTO);
        }

        dto.topics = topics;

        return dto;
    }

}
