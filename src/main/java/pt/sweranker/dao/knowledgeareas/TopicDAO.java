/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.dao.knowledgeareas;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.sweranker.dao.JPACrudDAO;
import pt.sweranker.persistence.entities.Language;
import pt.sweranker.persistence.entities.knowledgeareas.KnowledgeArea;
import pt.sweranker.persistence.entities.knowledgeareas.TropicanaTranslationAgain;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class TopicDAO extends JPACrudDAO<TropicanaTranslationAgain> {

    /**
     * @param entityClass
     */
    public TopicDAO() {
        super(TropicanaTranslationAgain.class);
    }

    @Override
    public TropicanaTranslationAgain findById(Long id) {
        return findById(id, Language.DEFAULT_LANGUAGE);
    }

    public TropicanaTranslationAgain findById(Long id, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        TypedQuery<TropicanaTranslationAgain> query = getEntityManager().createNamedQuery("TopicTranslations.findByIdAndLanguage", TropicanaTranslationAgain.class);
        query.setParameter("id", id);
        query.setParameter("language", language);

        return query.getResultList().get(0);
    }

    public List<TropicanaTranslationAgain> findTopicsOfKnowledgeArea(KnowledgeArea knowledgeArea, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        TypedQuery<TropicanaTranslationAgain> query = getEntityManager().createNamedQuery("TopicTranslations.findByKnowledgeArea", TropicanaTranslationAgain.class);
        query.setParameter("ka", knowledgeArea);
        query.setParameter("language", language);

        return query.getResultList();
    }

}
