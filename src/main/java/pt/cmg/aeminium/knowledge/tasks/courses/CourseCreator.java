/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.tasks.courses;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseDTO;
import pt.cmg.aeminium.knowledge.dao.identity.UserDAO;
import pt.cmg.aeminium.knowledge.dao.schools.CourseClassDAO;
import pt.cmg.aeminium.knowledge.dao.schools.CourseClassTopicDAO;
import pt.cmg.aeminium.knowledge.dao.schools.CourseDAO;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.TextContent;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.Course;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClass;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClassTopic;
import pt.cmg.aeminium.knowledge.tasks.localisation.TranslationEditor;

/**
 * @author Carlos Gonçalves
 */
@Singleton
public class CourseCreator {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private SchoolDAO schoolDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private CourseDAO courseDAO;

    @Inject
    private CourseClassDAO courseClassDAO;

    @Inject
    private CourseClassTopicDAO courseClassTopicDAO;

    @Inject
    private TranslationEditor translationEditor;

    public Course createCourse(CreateCourseDTO newCourse) {

        TextContent defaultSchoolName = translationEditor.createTranslatedTexts(newCourse.names);
        TextContent defaultCourseDescription = translationEditor.createTranslatedTexts(newCourse.descriptions);

        Course course = new Course();

        course.setNameTextContentId(defaultSchoolName.getId());
        course.setDescriptionContentId(defaultCourseDescription.getId());
        course.setAcronym(newCourse.acronym);
        course.setImage(newCourse.image);
        course.setYear(newCourse.year);

        // cached calls
        course.setSchool(schoolDAO.findById(newCourse.school));
        course.setCreatedBy(userDAO.findById(requestData.getUserId()));

        courseDAO.create(course);

        return course;
    }

    public Course editCourse(EditCourseDTO courseEdition, Long courseId) {

        Course courseToEdit = courseDAO.findById(courseId);

        translationEditor.updateTraslatedTexts(courseToEdit.getNameTextContentId(), courseEdition.names);
        translationEditor.updateTraslatedTexts(courseToEdit.getDescriptionContentId(), courseEdition.descriptions);

        if (StringUtils.isNotBlank(courseEdition.acronym)) {
            courseToEdit.setAcronym(courseEdition.acronym);
        }

        if (StringUtils.isNotBlank(courseEdition.image)) {
            courseToEdit.setImage(courseEdition.image);
        }

        if (courseEdition.school != null) {
            courseToEdit.setSchool(schoolDAO.findById(courseEdition.school));
        }

        return courseToEdit;
    }

    public CourseClass createClass(CreateCourseClassDTO newClassDTO) {
        return createClass(newClassDTO, newClassDTO.course);
    }

    public CourseClass createClass(CreateCourseClassDTO newClassDTO, Long courseId) {

        TextContent defaultClassName = translationEditor.createTranslatedTexts(newClassDTO.names);
        TextContent defaultClassDescription = translationEditor.createTranslatedTexts(newClassDTO.descriptions);

        CourseClass newClass = new CourseClass();

        newClass.setCourse(courseDAO.findById(courseId));
        newClass.setYear(newClassDTO.year);
        newClass.setSemester(newClassDTO.semester);
        newClass.setEcts(newClassDTO.ects);
        newClass.setOptional(newClassDTO.isOptional);
        newClass.setNameTextContentId(defaultClassName.getId());
        newClass.setDescriptionContentId(defaultClassDescription.getId());

        courseClassDAO.create(newClass, true);

        List<CourseClassTopic> topics = new ArrayList<>();
        for (var topicDTO : newClassDTO.topics) {

            TextContent defaultTopicDescription = translationEditor.createTranslatedTexts(topicDTO.descriptions);

            CourseClassTopic topic = new CourseClassTopic();
            topic.setDescriptionContentId(defaultTopicDescription.getId());
            topic.setOrder(topicDTO.order);
            topic.setCourseClass(newClass);

            courseClassTopicDAO.create(topic);

            topics.add(topic);
        }

        newClass.setCourseClassTopics(topics);

        return newClass;

    }

    public CourseClass editClass(EditCourseClassDTO newClassDTO, Long classId) {

        TextContent defaultClassName = translationEditor.createTranslatedTexts(newClassDTO.names);
        TextContent defaultClassDescription = translationEditor.createTranslatedTexts(newClassDTO.descriptions);

        CourseClass newClass = courseClassDAO.findById(classId);

        newClass.setYear(newClassDTO.year);
        newClass.setSemester(newClassDTO.semester);
        newClass.setEcts(newClassDTO.ects);
        newClass.setOptional(newClassDTO.isOptional);
        newClass.setNameTextContentId(defaultClassName.getId());
        newClass.setDescriptionContentId(defaultClassDescription.getId());

        return newClass;

    }

    public CourseClassTopic createTopic(CreateCourseClassTopicDTO newTopicDTO, Long classId) {

        TextContent defaultTopicDescription = translationEditor.createTranslatedTexts(newTopicDTO.descriptions);

        CourseClassTopic newTopic = new CourseClassTopic();

        CourseClass courseClass = courseClassDAO.findById(classId);

        newTopic.setCourseClass(courseClass);
        newTopic.setOrder(newTopicDTO.order);
        newTopic.setDescriptionContentId(defaultTopicDescription.getId());

        courseClassTopicDAO.create(newTopic, true);

        courseClass.addTopic(newTopic);

        return newTopic;

    }

}
