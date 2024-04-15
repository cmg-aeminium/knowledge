/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.api.resources.degrees.converters;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import pt.sweranker.api.filters.request.RequestContextData;
import pt.sweranker.api.filters.request.RequestData;
import pt.sweranker.api.resources.degrees.dto.request.DegreeSearchFilter;
import pt.sweranker.api.resources.degrees.dto.response.DegreeDTO;
import pt.sweranker.dao.degrees.DegreeDAO.DegreeFilterCriteria;
import pt.sweranker.persistence.entities.degrees.DegreeTranslation;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class DegreeConverter {

    @Inject
    @RequestData
    private RequestContextData requestData;

    public DegreeFilterCriteria toDegreeFilterCriteria(DegreeSearchFilter searchFilter) {
        return new DegreeFilterCriteria(searchFilter.university, requestData.getSelectedLanguage(), searchFilter.year, searchFilter.name);
    }

    public List<DegreeDTO> toDegreeDTOs(List<DegreeTranslation> degrees) {
        return degrees.stream().map(this::toDegreeDTO).collect(Collectors.toList());
    }

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
