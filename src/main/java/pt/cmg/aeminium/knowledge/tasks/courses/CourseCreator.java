/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.tasks.courses;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.common.entities.localisation.TextContent;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.CourseClassDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.CourseClassTopicDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.CourseDAO;
import pt.cmg.aeminium.datamodel.knowledge.dao.curricula.SchoolDAO;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.Course;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClass;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClassTopic;
import pt.cmg.aeminium.datamodel.users.dao.identity.UserDAO;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseDTO;
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

        TextContent defaultCourseName = translationEditor.createTranslatedTexts(newCourse.names);
        TextContent defaultCourseDescription = translationEditor.createTranslatedTexts(newCourse.descriptions);

        Course course = new Course();

        course.setNameTextContentId(defaultCourseName.getId());
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
        Course course = courseDAO.findById(courseId);

        newClass.setCourse(course);
        newClass.setYear(newClassDTO.year);
        newClass.setSemester(newClassDTO.semester);
        newClass.setEcts(newClassDTO.ects);
        newClass.setOptional(newClassDTO.isOptional);
        newClass.setNameTextContentId(defaultClassName.getId());
        newClass.setDescriptionContentId(defaultClassDescription.getId());

        courseClassDAO.create(newClass, true);

        course.addCourseClass(newClass);

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

    public CourseClass editClass(EditCourseClassDTO editClassDTO, Long classId) {

        CourseClass classToEdit = courseClassDAO.findById(classId);

        translationEditor.updateTraslatedTexts(classToEdit.getNameTextContentId(), editClassDTO.names);
        translationEditor.updateTraslatedTexts(classToEdit.getDescriptionContentId(), editClassDTO.descriptions);

        classToEdit.setYear(editClassDTO.year);
        classToEdit.setSemester(editClassDTO.semester);
        classToEdit.setEcts(editClassDTO.ects);
        classToEdit.setOptional(editClassDTO.isOptional);

        return classToEdit;

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

    public CourseClassTopic editTopic(EditCourseClassTopicDTO editTopicDTO, Long topicId) {

        CourseClassTopic topicToEdit = courseClassTopicDAO.findById(topicId);

        if (editTopicDTO.descriptions != null && !editTopicDTO.descriptions.isEmpty()) {
            translationEditor.updateTraslatedTexts(topicToEdit.getDescriptionContentId(), editTopicDTO.descriptions);
        }

        if (editTopicDTO.order != null) {
            topicToEdit.setOrder(editTopicDTO.order);
        }

        return topicToEdit;
    }

    public void deleteTopic(Long topicId) {
        CourseClassTopic topicToDelete = courseClassTopicDAO.findById(topicId);
        CourseClass courseClass = topicToDelete.getCourseClass();

        if (topicToDelete != null) {
            courseClassTopicDAO.remove(topicToDelete);
            courseClass.removeTopic(topicToDelete);
        }
    }

}
