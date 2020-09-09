package pt.sweranker.dao.knowledgeareas;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.sweranker.dao.JPACrudDAO;
import pt.sweranker.persistence.entities.Language;
import pt.sweranker.persistence.entities.knowledgeareas.KnowledgeAreaTranslation;

@Stateless
public class KnowledgeAreaDAO extends JPACrudDAO<KnowledgeAreaTranslation> {

    public KnowledgeAreaDAO() {
        super(KnowledgeAreaTranslation.class);
    }

    /**
     * Fetches a KnowledgeAreaTranslation by its id in the default language, returning a default if it didn't find any result of the
     * required language
     *
     * @param id
     * @return
     */
    @Override
    public KnowledgeAreaTranslation findById(Long id) {

        TypedQuery<KnowledgeAreaTranslation> query = getEntityManager().createNamedQuery("KnowledgeAreaTranslation.findByIdAndLanguage", KnowledgeAreaTranslation.class);
        query.setParameter("id", id);
        query.setParameter("language", Language.DEFAULT_LANGUAGE);

        return query.getResultList().get(0);
    }

    /**
     * Fetches a KnowledgeAreaTranslation by its id and for a given language, returning a default if it didn't find any result of the
     * required language
     *
     * @param id
     * @param language
     * @return
     */
    public KnowledgeAreaTranslation findById(Long id, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        TypedQuery<KnowledgeAreaTranslation> query = getEntityManager().createNamedQuery("KnowledgeAreaTranslation.findByIdAndLanguage", KnowledgeAreaTranslation.class);
        query.setParameter("id", id);
        query.setParameter("language", language);

        return query.getResultList().get(0);
    }

}
