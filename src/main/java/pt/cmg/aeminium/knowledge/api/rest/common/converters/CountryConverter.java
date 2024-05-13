/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.common.converters;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pt.cmg.aeminium.knowledge.api.rest.common.dto.response.CountryDTO;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.Country;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class CountryConverter {

    @Inject
    private HazelcastCache translationCache;

    public CountryDTO toCountryDTO(Country country) {
        CountryDTO dto = new CountryDTO();
        dto.id = country.getId();
        dto.alpha2code = country.getAlpha2Code();
        dto.name = translationCache.getTranslatedText(country.getNameTextContentId());
        return dto;
    }

}
