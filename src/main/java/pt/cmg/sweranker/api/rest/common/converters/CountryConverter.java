/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.common.converters;

import pt.cmg.sweranker.api.rest.common.dto.response.CountryDTO;
import pt.cmg.sweranker.persistence.entities.localisation.Country;

/**
 * @author Carlos Gonçalves
 */
public class CountryConverter {

    public static CountryDTO toCountryDTO(Country country) {
        CountryDTO dto = new CountryDTO();
        dto.id = country.getId();
        dto.alpha2code = country.getAlpha2Code();
        dto.name = country.getName();
        return dto;
    }

}
