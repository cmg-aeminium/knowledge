/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.dao.knowledgeareas;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.sweranker.dao.JPACrudDAO;
import pt.sweranker.persistence.entities.Language;
import pt.sweranker.persistence.entities.knowledgeareas.KnowledgeArea;
import pt.sweranker.persistence.entities.knowledgeareas.KnowledgeTopic;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class TopicDAO extends JPACrudDAO<KnowledgeTopic> {

    /**
     * @param entityClass
     */
    public TopicDAO() {
        super(KnowledgeTopic.class);
    }

    @Override
    public KnowledgeTopic findById(Long id) {
        return findById(id, Language.DEFAULT_LANGUAGE);
    }

    public KnowledgeTopic findById(Long id, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        TypedQuery<KnowledgeTopic> query = getEntityManager().createNamedQuery("TopicTranslations.findByIdAndLanguage", KnowledgeTopic.class);
        query.setParameter("id", id);
        query.setParameter("language", language);

        return query.getResultList().get(0);
    }

    public List<KnowledgeTopic> findTopicsOfKnowledgeArea(KnowledgeArea knowledgeArea, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        TypedQuery<KnowledgeTopic> query = getEntityManager().createNamedQuery("TopicTranslations.findByKnowledgeArea", KnowledgeTopic.class);
        query.setParameter("ka", knowledgeArea);
        query.setParameter("language", language);

        return query.getResultList();
    }

}
