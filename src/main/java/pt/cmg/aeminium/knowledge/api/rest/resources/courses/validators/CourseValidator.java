/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CourseSearchFilter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateCourseDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditCourseDTO;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.dao.identity.UserDAO;
import pt.cmg.aeminium.knowledge.dao.schools.CourseClassDAO;
import pt.cmg.aeminium.knowledge.dao.schools.CourseClassTopicDAO;
import pt.cmg.aeminium.knowledge.dao.schools.CourseDAO;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.identity.User;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.Course;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClass;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.CourseClassTopic;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class CourseValidator {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private SchoolDAO schoolDAO;

    @Inject
    private CourseDAO courseDAO;

    @Inject
    private CourseClassDAO courseClassDAO;

    @Inject
    private CourseClassTopicDAO courseClassTopicDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private HazelcastCache textCache;

    public Optional<List<ErrorDTO>> isSearchFilterValid(CourseSearchFilter searchFilter) {
        List<ErrorDTO> errors = new ArrayList<>();

        if (searchFilter.school != null) {
            if (schoolDAO.findById(searchFilter.school) == null) {
                errors.add(new ErrorDTO(1, "School does not exist"));
            }
        }

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

    public Optional<List<ErrorDTO>> isCourseCreationValid(CreateCourseDTO newCourse) {
        List<ErrorDTO> errors = new ArrayList<>();

        if (newCourse.school != null) {
            if (schoolDAO.findById(newCourse.school) == null) {
                errors.add(new ErrorDTO(1, "School does not exist"));
            }
        }

        for (var entry : newCourse.names) {
            Course course = courseDAO.findByName(entry.language, entry.value);

            if (course != null) {
                errors.add(new ErrorDTO(2, "Course with same name already exists for language " + entry.language.getName()));
            }
        }

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

    public Optional<List<ErrorDTO>> isCourseEditionValid(EditCourseDTO courseEditionDTO, Long courseId) {
        List<ErrorDTO> errors = new ArrayList<>();

        Course courseToEdit = courseDAO.findById(courseId);
        if (courseToEdit == null) {
            return Optional.of(List.of(new ErrorDTO(1, "Course does not exist: " + courseId)));
        }

        User currentUser = userDAO.findById(requestData.getUserId());

        if (!courseToEdit.getCreatedBy().equals(currentUser)) {
            return Optional.of(List.of(new ErrorDTO(2, "This Course does not belong to calling user")));
        }

        if (courseEditionDTO.names != null) {
            for (var entry : courseEditionDTO.names) {

                if (!textCache.containsText(courseToEdit.getNameTextContentId(), entry.language)) {
                    errors.add(new ErrorDTO(3, "No text exists for the given language: " + entry.language.getName()));
                }

            }
        }

        if (courseEditionDTO.descriptions != null) {
            for (var entry : courseEditionDTO.descriptions) {

                if (!textCache.containsText(courseToEdit.getDescriptionContentId(), entry.language)) {
                    errors.add(new ErrorDTO(3, "No text exists for the given language: " + entry.language.getName()));
                }

            }
        }

        if (courseEditionDTO.acronym != null && courseEditionDTO.acronym.isBlank()) {
            errors.add(new ErrorDTO(4, "Empty acronym used"));
        }

        if (courseEditionDTO.school != null && schoolDAO.findById(courseEditionDTO.school) == null) {
            errors.add(new ErrorDTO(5, "No valid school found for id: " + courseEditionDTO.school));
        }

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

    public Optional<List<ErrorDTO>> isClassCreationValid(CreateCourseClassDTO newClass) {
        return isClassCreationValid(newClass, newClass.course);
    }

    public Optional<List<ErrorDTO>> isClassCreationValid(CreateCourseClassDTO newClass, Long courseId) {

        Course course = courseDAO.findById(courseId);
        if (course == null) {
            return Optional.of(List.of(new ErrorDTO(1, "Course does not exist")));
        }

        if (userDAO.findById(requestData.getUserId()) != course.getCreatedBy()) {
            return Optional.of(List.of(new ErrorDTO(2, "Class creator is not the same as the course creator")));
        }

        return Optional.empty();
    }

    public Optional<List<ErrorDTO>> isClassEditionValid(Long classId, EditCourseClassDTO courseEditionDTO) {

        CourseClass courseClass = courseClassDAO.findById(classId);
        if (courseClass == null) {
            return Optional.of(List.of(new ErrorDTO(1, "Course Class does not exist")));
        }

        if (!courseClass.getCourse().getCreatedBy().getId().equals(requestData.getUserId())) {
            return Optional.of(List.of(new ErrorDTO(2, "Class not created by user")));
        }

        return Optional.empty();
    }

    public Optional<List<ErrorDTO>> isCourseClassEditionValid(Long courseId, Long classId, EditCourseClassDTO courseEditionDTO) {

        CourseClass courseClass = courseClassDAO.findById(classId);
        if (courseClass == null) {
            return Optional.of(List.of(new ErrorDTO(1, "Course Class does not exist")));
        }

        if (!courseClass.getCourse().getId().equals(courseId)) {
            return Optional.of(List.of(new ErrorDTO(2, "Course Class does not belong to given degree")));
        }

        Course course = courseDAO.findById(courseId);
        if (!course.getCreatedBy().getId().equals(requestData.getUserId())) {
            return Optional.of(List.of(new ErrorDTO(3, "Class not created by user")));
        }

        return Optional.empty();
    }

    public Optional<List<ErrorDTO>> isTopicEditionValid(Long classId, Long topicId, EditCourseClassTopicDTO topicEditionDTO) {

        CourseClassTopic courseClass = courseClassTopicDAO.findById(topicId);
        if (courseClass == null) {
            return Optional.of(List.of(new ErrorDTO(1, "Course Class Topic does not exist")));
        }

        if (!courseClass.getCourseClass().getId().equals(classId)) {
            return Optional.of(List.of(new ErrorDTO(2, "Course Class Topic does not belong to given Class")));
        }

        if (topicEditionDTO.order != null && topicEditionDTO.order < 0) {
            return Optional.of(List.of(new ErrorDTO(3, "No negative order can be used")));
        }

        return Optional.empty();
    }

}
