/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.dao.degrees;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.sweranker.dao.JPACrudDAO;
import pt.sweranker.persistence.entities.Language;
import pt.sweranker.persistence.entities.degrees.DegreeTranslation;
import pt.sweranker.persistence.entities.degrees.University;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class DegreeDAO extends JPACrudDAO<DegreeTranslation> {

    private static final String BASE_SELECT_DEGREE_QUERY = "SELECT d "
        + "FROM DegreeTranslations d JOIN FETCH d.degree deg ";

    public static final String AND = " AND ";

    public DegreeDAO() {
        super(DegreeTranslation.class);
    }

    /**
     * Fetches a DegreeTranslation by its id in the default language, returning a default if it didn't find any result of the
     * required language
     *
     * @param id
     * @return
     */
    @Override
    public DegreeTranslation findById(Long id) {

        TypedQuery<DegreeTranslation> query = getEntityManager().createNamedQuery(DegreeTranslation.QUERY_FIND_BY_ID_TRANSLATED, DegreeTranslation.class);
        query.setParameter(DegreeTranslation.PARAMETER_ID, id);
        query.setParameter(DegreeTranslation.PARAMETER_LANGUAGE, Language.DEFAULT_LANGUAGE);

        return query.getResultList().get(0);
    }

    /**
     * Searches for Degrees with some filtering criteria
     *
     * @param filterParameters
     * @return
     */
    public List<DegreeTranslation> findFiltered(DegreeFilterCriteria filterParameters) {

        StringBuilder queryText = new StringBuilder(BASE_SELECT_DEGREE_QUERY);
        String prefix = " WHERE ";

        if (filterParameters.university != null) {
            queryText.append(prefix).append("deg.university = :university ");
            prefix = AND;
        }

        if (filterParameters.year != null) {
            queryText.append(prefix).append("deg.year = :year ");
            prefix = AND;
        }

        TypedQuery<DegreeTranslation> query = getEntityManager().createQuery(queryText.toString(), DegreeTranslation.class);

        setDegreeQueryParameters(query, filterParameters);

        return query.getResultList();
    }

    private void setDegreeQueryParameters(TypedQuery<DegreeTranslation> query, DegreeFilterCriteria filterParameters) {

        if (filterParameters.university != null) {
            query.setParameter("university", filterParameters.university);
        }

        if (filterParameters.year != null) {
            query.setParameter("year", filterParameters.year);
        }
    }

    public static class DegreeFilterCriteria {

        /**
         * @param university
         * @param language
         * @param year
         * @param name
         */
        public DegreeFilterCriteria(University university, Language language, Integer year, String name) {
            super();
            this.university = university;
            this.language = language;
            this.year = year;
            this.name = name;
        }

        public University university;
        public Language language;
        public Integer year;
        public String name;
    }
}
