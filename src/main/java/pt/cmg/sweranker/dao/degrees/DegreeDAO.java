/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.dao.degrees;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.cmg.sweranker.dao.JPACrudDAO;
import pt.cmg.sweranker.persistence.entities.Language;
import pt.cmg.sweranker.persistence.entities.degrees.Course;
import pt.cmg.sweranker.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class DegreeDAO extends JPACrudDAO<Course> {

    private static final String BASE_SELECT_DEGREE_QUERY = "SELECT d "
        + "FROM DegreeTranslations d JOIN FETCH d.degree deg ";

    public static final String AND = " AND ";

    public DegreeDAO() {
        super(Course.class);
    }

    /**
     * Searches for Degrees with some filtering criteria
     *
     * @param filterParameters
     * @return
     */
    public List<Course> findFiltered(DegreeFilterCriteria filterParameters) {

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

        TypedQuery<Course> query = getEntityManager().createQuery(queryText.toString(), Course.class);

        setDegreeQueryParameters(query, filterParameters);

        return query.getResultList();
    }

    private void setDegreeQueryParameters(TypedQuery<Course> query, DegreeFilterCriteria filterParameters) {

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
        public DegreeFilterCriteria(School university, Language language, Integer year, String name) {
            super();
            this.university = university;
            this.language = language;
            this.year = year;
            this.name = name;
        }

        public School university;
        public Language language;
        public Integer year;
        public String name;
    }
}
