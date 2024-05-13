/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.dao.knowledgeareas;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.cmg.aeminium.knowledge.dao.JPACrudDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeArea;
import pt.cmg.aeminium.knowledge.persistence.entities.knowledgebodies.KnowledgeTopic;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.Language;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class KnowledgeTopicDAO extends JPACrudDAO<KnowledgeTopic> {

    /**
     * @param entityClass
     */
    public KnowledgeTopicDAO() {
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
