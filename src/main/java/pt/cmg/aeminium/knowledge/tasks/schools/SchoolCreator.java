/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.tasks.schools;

import java.util.Iterator;
import javax.ejb.Singleton;
import javax.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestContextData;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.RequestData;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.dao.identity.UserDAO;
import pt.cmg.aeminium.knowledge.dao.localisation.CountryDAO;
import pt.cmg.aeminium.knowledge.dao.localisation.TextContentDAO;
import pt.cmg.aeminium.knowledge.dao.localisation.TranslatedTextDAO;
import pt.cmg.aeminium.knowledge.dao.schools.SchoolDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.Language;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.TextContent;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.TranslatedText;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.School;
import pt.cmg.aeminium.knowledge.tasks.schools.CreateSchoolDTO.TranslatedName;

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
    private TextContentDAO textContentDAO;

    @Inject
    private TranslatedTextDAO translatedTextDAO;

    @Inject
    private CountryDAO countryDAO;

    @Inject
    private HazelcastCache textCache;

    @Inject
    private UserDAO userDAO;

    public School createSchool(CreateSchoolDTO newSchool) {

        TranslatedName defaultTranslation = null;
        for (Iterator<TranslatedName> iterator = newSchool.names.iterator(); iterator.hasNext();) {
            TranslatedName element = iterator.next();
            if (element.language == Language.DEFAULT_LANGUAGE) {
                iterator.remove();
            }
            defaultTranslation = element;
        }

        TextContent defaultText = null;
        if (defaultTranslation != null) {
            defaultText = new TextContent(Language.DEFAULT_LANGUAGE, defaultTranslation.value);
        } else {
            defaultText = new TextContent(Language.DEFAULT_LANGUAGE, "???");
        }

        textContentDAO.create(defaultText, true);
        textCache.putTranslation(defaultText);

        for (TranslatedName otherTranslation : newSchool.names) {
            TranslatedText translation = new TranslatedText(defaultText.getId(), otherTranslation.language, otherTranslation.value);

            translatedTextDAO.create(translation);
            textCache.putTranslation(translation);
        }

        School school = new School();

        school.setCountry(countryDAO.findById(newSchool.country));
        school.setNameTextContentId(defaultText.getId());

        // cached call
        school.setCreatedBy(userDAO.findById(requestData.getUserId()));

        schoolDAO.create(school);

        return school;
    }

}
