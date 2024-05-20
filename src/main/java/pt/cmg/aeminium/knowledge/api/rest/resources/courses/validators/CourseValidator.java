/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CourseSearchFilter;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class CourseValidator {

    @Inject
    private SchoolDAO schoolDAO;

    public Optional<List<ErrorDTO>> isSearchFilterValid(CourseSearchFilter searchFilter) {
        List<ErrorDTO> errors = new ArrayList<>();

        if (searchFilter.school != null) {
            if (schoolDAO.findById(searchFilter.school) == null) {
                errors.add(new ErrorDTO(1, "School does not exist"));
            }
        }

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }

}
