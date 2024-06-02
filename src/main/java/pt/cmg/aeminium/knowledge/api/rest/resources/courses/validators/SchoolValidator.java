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
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateSchoolDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditSchoolDTO;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.dao.identity.UserDAO;
import pt.cmg.aeminium.knowledge.dao.localisation.CountryDAO;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.identity.User;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.School;
import pt.cmg.jakartautils.errors.ErrorDTO;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class SchoolValidator {

    @Inject
    @RequestData
    private RequestContextData requestData;

    @Inject
    private SchoolDAO schoolDAO;

    @Inject
    private CountryDAO countryDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private HazelcastCache textCache;

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

    public Optional<List<ErrorDTO>> isEditionValid(EditSchoolDTO schoolEditionDTO, Long schoolId) {
        List<ErrorDTO> errors = new ArrayList<>();

        School schoolToEdit = schoolDAO.findById(schoolId);
        if (schoolToEdit == null) {
            return Optional.of(List.of(new ErrorDTO(1, "School does not exist: " + schoolId)));
        }

        User currentUser = userDAO.findById(requestData.getUserId());

        if (!schoolToEdit.getCreatedBy().equals(currentUser)) {
            return Optional.of(List.of(new ErrorDTO(2, "This school does not belong to calling user")));
        }

        for (var entry : schoolEditionDTO.names) {

            if (!textCache.containsText(schoolToEdit.getId(), entry.language)) {
                errors.add(new ErrorDTO(3, "No text exists for the given language: " + entry.language.getName()));
            }

        }

        if (countryDAO.findById(schoolEditionDTO.country) == null) {
            errors.add(new ErrorDTO(4, "Given country does not exist " + schoolEditionDTO.country));
        }

        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }
}
