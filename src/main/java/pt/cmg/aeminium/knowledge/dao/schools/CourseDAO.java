/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.dao.schools;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import pt.cmg.aeminium.knowledge.dao.JPACrudDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.Course;
import pt.cmg.aeminium.knowledge.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class CourseDAO extends JPACrudDAO<Course> {

    private static final String BASE_SELECT_DEGREE_QUERY = "SELECT c FROM Course c ";

    public static final String AND = " AND ";

    public CourseDAO() {
        super(Course.class);
    }

    /**
     * Searches for Degrees with some filtering criteria
     */
    public List<Course> findFiltered(DegreeFilterCriteria filterParameters) {

        StringBuilder selectText = new StringBuilder(BASE_SELECT_DEGREE_QUERY);
        StringBuilder filterText = new StringBuilder();
        String prefix = " WHERE ";

        if (filterParameters.school != null) {
            filterText.append(prefix).append("c.school = :school ");
            prefix = AND;
        }

        if (filterParameters.year != null) {
            filterText.append(prefix).append("c.year = :year ");
            prefix = AND;
        }

        if (StringUtils.isNotBlank(filterParameters.acronym)) {
            filterText.append(prefix).append("c.acronym = :acronym ");
            prefix = AND;
        }

        if (StringUtils.isNotBlank(filterParameters.name)) {
            filterText.append(prefix).append("c.name = :name ");
            prefix = AND;
        }

        String queryText = selectText.append(filterText).toString();
        TypedQuery<Course> query = getEntityManager().createQuery(queryText, Course.class);

        setDegreeQueryParameters(query, filterParameters);

        return query.getResultList();
    }

    private void setDegreeQueryParameters(TypedQuery<Course> query, DegreeFilterCriteria filterParameters) {

        if (filterParameters.school != null) {
            query.setParameter("school", filterParameters.school);
        }

        if (filterParameters.year != null) {
            query.setParameter("year", filterParameters.year);
        }

        if (StringUtils.isNotBlank(filterParameters.acronym)) {
            query.setParameter("acronym", filterParameters.acronym);
        }

        if (StringUtils.isNotBlank(filterParameters.name)) {
            query.setParameter("name", filterParameters.name);
        }
    }

    public static class DegreeFilterCriteria {

        public DegreeFilterCriteria(School school, Integer year, String name, String acronym) {
            super();
            this.school = school;
            this.year = year;
            this.name = name;
            this.acronym = acronym;
        }

        public School school;
        public Integer year;
        public String name;
        public String acronym;
    }
}
