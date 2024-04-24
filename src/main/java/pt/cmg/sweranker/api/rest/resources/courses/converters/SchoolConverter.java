/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.courses.converters;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pt.cmg.sweranker.api.rest.common.converters.CountryConverter;
import pt.cmg.sweranker.api.rest.resources.courses.dto.response.SchoolDTO;
import pt.cmg.sweranker.cache.HazelcastCache;
import pt.cmg.sweranker.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class SchoolConverter {

    @Inject
    private HazelcastCache translationCache;

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
