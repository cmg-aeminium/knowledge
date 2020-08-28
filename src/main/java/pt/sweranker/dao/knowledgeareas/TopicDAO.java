/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.dao.knowledgeareas;

import javax.persistence.TypedQuery;
import pt.sweranker.dao.JPACrudDAO;
import pt.sweranker.persistence.Language;
import pt.sweranker.persistence.knowledgeareas.TopicTranslation;

/**
 * @author Carlos Gonçalves
 */
public class TopicDAO extends JPACrudDAO<TopicTranslation> {

    /**
     * @param entityClass
     */
    public TopicDAO() {
        super(TopicTranslation.class);
    }

    @Override
    public TopicTranslation findById(Long id) {

        TypedQuery<TopicTranslation> query = getEntityManager().createNamedQuery("TopicTranslations.findByIdAndLanguage", TopicTranslation.class);
        query.setParameter("id", id);
        query.setParameter("language", Language.DEFAULT_LANGUAGE);

        return query.getResultList().get(0);
    }

}
