/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.api.rest.resources.courses.converters;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pt.cmg.aeminium.datamodel.knowledge.entities.curricula.School;
import pt.cmg.aeminium.knowledge.api.rest.common.converters.CountryConverter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.dto.response.SchoolDTO;
import pt.cmg.aeminium.knowledge.cache.TextTranslationCache;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class SchoolConverter {

    @Inject
    private TextTranslationCache translationCache;

    @Inject
    private CountryConverter countryConverter;

    public List<SchoolDTO> toSchoolDTOs(List<School> schools) {
        return schools.stream().map(this::toSchoolDTO).collect(Collectors.toList());
    }

    public SchoolDTO toSchoolDTO(School school) {
        SchoolDTO dto = new SchoolDTO();
        dto.id = school.getId();
        dto.name = translationCache.getTranslatedText(school.getNameTextContentId());
        dto.country = countryConverter.toCountryDTO(school.getCountry());
        dto.createdAt = school.getCreatedAt();

        return dto;
    }

}
