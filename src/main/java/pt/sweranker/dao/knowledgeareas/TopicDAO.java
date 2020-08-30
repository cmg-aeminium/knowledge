/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.dao.knowledgeareas;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.sweranker.dao.JPACrudDAO;
import pt.sweranker.persistence.Language;
import pt.sweranker.persistence.knowledgeareas.KnowledgeArea;
import pt.sweranker.persistence.knowledgeareas.TopicTranslation;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class TopicDAO extends JPACrudDAO<TopicTranslation> {

    /**
     * @param entityClass
     */
    public TopicDAO() {
        super(TopicTranslation.class);
    }

    @Override
    public TopicTranslation findById(Long id) {
        return findById(id, Language.DEFAULT_LANGUAGE);
    }

    public TopicTranslation findById(Long id, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        TypedQuery<TopicTranslation> query = getEntityManager().createNamedQuery("TopicTranslations.findByIdAndLanguage", TopicTranslation.class);
        query.setParameter("id", id);
        query.setParameter("language", language);

        return query.getResultList().get(0);
    }

    public List<TopicTranslation> findTopicsOfKnowledgeArea(KnowledgeArea knowledgeArea, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        TypedQuery<TopicTranslation> query = getEntityManager().createNamedQuery("TopicTranslations.findByKnowledgeArea", TopicTranslation.class);
        query.setParameter("ka", knowledgeArea);
        query.setParameter("language", language);

        return query.getResultList();
    }

}
