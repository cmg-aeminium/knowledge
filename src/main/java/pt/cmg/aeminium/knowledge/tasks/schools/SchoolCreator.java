/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.tasks.schools;

import javax.ejb.Singleton;
import javax.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.CreateSchoolDTO;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.request.EditSchoolDTO;
import pt.cmg.aeminium.knowledge.dao.identity.UserDAO;
import pt.cmg.aeminium.knowledge.dao.localisation.CountryDAO;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.TextContent;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.School;
import pt.cmg.aeminium.knowledge.tasks.localisation.TranslationEditor;

/**
 * @author Carlos Gonçalves
 */
@Singleton
public class SchoolCreator {

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
    private TranslationEditor translationEditor;

    public School createSchool(CreateSchoolDTO newSchool) {

        TextContent defaultSchoolName = translationEditor.createTranslatedTexts(newSchool.names);

        School school = new School();

        school.setCountry(countryDAO.findById(newSchool.country));
        school.setNameTextContentId(defaultSchoolName.getId());

        // cached call
        school.setCreatedBy(userDAO.findById(requestData.getUserId()));

        schoolDAO.create(school);

        return school;
    }

    public School editSchool(EditSchoolDTO schoolEdition, Long schoolId) {

        School schoolToEdit = schoolDAO.findById(schoolId);

        translationEditor.updateTraslatedTexts(schoolToEdit.getNameTextContentId(), schoolEdition.names);

        schoolToEdit.setCountry(countryDAO.findById(schoolEdition.country));

        return schoolToEdit;
    }

}
