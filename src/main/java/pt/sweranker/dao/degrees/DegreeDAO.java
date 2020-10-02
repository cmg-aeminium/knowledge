/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.dao.degrees;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.sweranker.dao.JPACrudDAO;
import pt.sweranker.persistence.entities.Language;
import pt.sweranker.persistence.entities.degrees.DegreeTranslation;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class DegreeDAO extends JPACrudDAO<DegreeTranslation> {

    public DegreeDAO() {
        super(DegreeTranslation.class);
    }

    /**
     * Fetches a KnowledgeAreaTranslation by its id in the default language, returning a default if it didn't find any result of the
     * required language
     *
     * @param id
     * @return
     */
    @Override
    public DegreeTranslation findById(Long id) {

        TypedQuery<DegreeTranslation> query = getEntityManager().createNamedQuery("DegreeTranslation.findByIdAndLanguage", DegreeTranslation.class);
        query.setParameter("id", id);
        query.setParameter("language", Language.DEFAULT_LANGUAGE);

        return query.getResultList().get(0);
    }
}
