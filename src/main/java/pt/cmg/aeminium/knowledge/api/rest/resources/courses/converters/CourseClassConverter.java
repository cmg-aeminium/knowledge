/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters;

import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClass;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.CourseClassTopic;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassDetailDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassDetailDTO.ClassTopicDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.CourseClassTopicDTO;
import pt.cmg.aeminium.knowledge.cache.TextTranslationCache;

/**
 * @author Carlos Gonçalves
 */
@Dependent
public class CourseClassConverter {

    @Inject
    private TextTranslationCache translationCache;

    public List<CourseClassDTO> toCourseClassesDTO(List<CourseClass> classes) {
        return classes.stream().map(this::toCourseClassDTO).toList();
    }

    public CourseClassDTO toCourseClassDTO(CourseClass courseClass) {
        return new CourseClassDTO(
            courseClass.getId(),
            courseClass.getYear(),
            courseClass.getSemester(),
            translationCache.getTranslatedText(courseClass.getNameTextContentId()),
            courseClass.getEcts());
    }

    public List<CourseClassTopicDTO> toCourseClasseTopicDTOs(List<CourseClassTopic> topics) {
        return topics.stream().map(this::toCourseClasseTopicDTO).toList();
    }

    public CourseClassTopicDTO toCourseClasseTopicDTO(CourseClassTopic topic) {

        if (topic == null) {
            return null;
        }

        return new CourseClassTopicDTO(
            topic.getId(),
            translationCache.getTranslatedText(topic.getDescriptionContentId()),
            topic.getOrder());

    }

    public CourseClassDetailDTO toCourseClassDetailedDTO(CourseClass courseClass) {

        List<ClassTopicDTO> topics = new ArrayList<>();
        for (var topic : courseClass.getCourseClassTopics()) {

            ClassTopicDTO topicDTO = new ClassTopicDTO(
                topic.getId(),
                translationCache.getTranslatedText(topic.getDescriptionContentId()),
                topic.getOrder());

            topics.add(topicDTO);
        }

        return new CourseClassDetailDTO(
            courseClass.getId(),
            courseClass.getYear(),
            courseClass.getSemester(),
            translationCache.getTranslatedText(courseClass.getNameTextContentId()),
            translationCache.getTranslatedText(courseClass.getDescriptionContentId()),
            courseClass.getEcts(),
            courseClass.isOptional(),
            courseClass.getCreatedAt(),
            topics);

    }

}
