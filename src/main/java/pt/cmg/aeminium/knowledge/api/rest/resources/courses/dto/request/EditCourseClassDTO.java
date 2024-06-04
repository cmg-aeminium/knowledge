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
public class EditCourseClassDTO {
    public Integer year;
    public Integer semester;
    public Double ects;
    public boolean isOptional; // NOTE: implicitly not null and default value = false as per JDK
    public HashSet<LocalisedTextDTO> names;
    public HashSet<LocalisedTextDTO> descriptions;
}
