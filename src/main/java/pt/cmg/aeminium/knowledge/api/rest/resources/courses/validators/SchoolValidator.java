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
import pt.cmg.aeminium.knowledge.dao.localisation.CountryDAO;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.School;
import pt.cmg.aeminium.knowledge.tasks.schools.CreateSchoolDTO;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class SchoolValidator {

    @Inject
    private SchoolDAO schoolDAO;

    @Inject
    private CountryDAO countryDAO;

    public Optional<List<ErrorDTO>> isCreationValid(CreateSchoolDTO newSchoolDTO) {
        List<ErrorDTO> errors = new ArrayList<>();

        for (var entry : newSchoolDTO.names) {
            School school = schoolDAO.findByName(entry.language, entry.value);

            if (school != null) {
                errors.add(new ErrorDTO(1, "School with same name already exists for language " + entry.language.getName()));
            }
        }

        if (countryDAO.findById(newSchoolDTO.country) == null) {
            errors.add(new ErrorDTO(2, "Given country does not exist " + newSchoolDTO.country));
        }

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }
}
