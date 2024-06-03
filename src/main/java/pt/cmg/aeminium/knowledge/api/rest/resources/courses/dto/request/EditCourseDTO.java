/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request;

import java.util.HashSet;
import pt.cmg.aeminium.knowledge.tasks.localisation.LocalisedTextDTO;

/**
 * @author Carlos Gonçalves
 */
public class EditCourseDTO {

    public String acronym;
    public String image;
    public Long school;
    public Integer year;
    public HashSet<LocalisedTextDTO> names;
    public HashSet<LocalisedTextDTO> descriptions;

}
