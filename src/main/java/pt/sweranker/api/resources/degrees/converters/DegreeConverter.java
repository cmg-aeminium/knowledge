/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.api.resources.degrees.converters;

import javax.ejb.Stateless;
import pt.sweranker.api.resources.degrees.dto.DegreeDTO;
import pt.sweranker.persistence.entities.degrees.DegreeTranslation;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class DegreeConverter {

    public DegreeDTO toDegreeDTO(DegreeTranslation degree) {
        DegreeDTO dto = new DegreeDTO();
        dto.id = degree.getId();
        dto.acronym = degree.getDegree().getAcronym();
        dto.name = degree.getName();
        dto.description = degree.getDescription();
        dto.image = degree.getDegree().getImage();
        dto.university = degree.getDegree().getUniversity();
        dto.year = degree.getDegree().getYear();

        return dto;
    }

}
