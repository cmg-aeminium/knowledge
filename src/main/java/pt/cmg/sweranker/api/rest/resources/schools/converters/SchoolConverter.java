/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.api.rest.resources.schools.converters;

import java.util.List;
import java.util.stream.Collectors;
import pt.cmg.sweranker.api.rest.common.converters.CountryConverter;
import pt.cmg.sweranker.api.rest.resources.schools.dto.response.SchoolDTO;
import pt.cmg.sweranker.persistence.entities.schools.School;

/**
 * @author Carlos Goçalves
 */
public class SchoolConverter {

    public static List<SchoolDTO> toSchoolDTOs(List<School> schools) {
        return schools.stream().map(SchoolConverter::toSchoolDTO).collect(Collectors.toList());
    }

    public static SchoolDTO toSchoolDTO(School school) {
        SchoolDTO dto = new SchoolDTO();
        dto.id = school.getId();
        dto.name = school.getName();
        dto.country = CountryConverter.toCountryDTO(school.getCountry());

        return dto;
    }

}
