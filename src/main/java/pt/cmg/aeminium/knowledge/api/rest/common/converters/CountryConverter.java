/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.common.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.common.entities.localisation.Country;
import pt.cmg.aeminium.knowledge.api.rest.common.dto.response.CountryDTO;
import pt.cmg.aeminium.knowledge.cache.TextTranslationCache;

/**
 * @author Carlos Gonçalves
 */
@ApplicationScoped
public class CountryConverter {

    @Inject
    private TextTranslationCache translationCache;

    public CountryDTO toCountryDTO(Country country) {
        return new CountryDTO(country.getId(),
            country.getAlpha2Code(),
            translationCache.getTranslatedText(country.getNameTextContentId()));
    }

}
