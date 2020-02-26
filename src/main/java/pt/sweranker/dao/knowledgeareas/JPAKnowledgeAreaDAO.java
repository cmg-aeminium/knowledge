package pt.sweranker.dao.knowledgeareas;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import pt.sweranker.dao.JPACrudDAO;
import pt.sweranker.persistence.Language;
import pt.sweranker.persistence.knowledgeareas.KnowledgeAreaTranslation;

@Stateless
public class JPAKnowledgeAreaDAO extends JPACrudDAO<KnowledgeAreaTranslation> implements KnowledgeAreaDAO {

    public JPAKnowledgeAreaDAO() {
        super(KnowledgeAreaTranslation.class);
    }

    @Override
    public KnowledgeAreaTranslation findById(Long id) {

        TypedQuery<KnowledgeAreaTranslation> query = getEntityManager().createNamedQuery("KnowledgeAreaTranslations.findByIdAndLanguage", KnowledgeAreaTranslation.class);
        query.setParameter("id", id);
        query.setParameter("language", Language.DEFAULT_LANGUAGE);

        return query.getResultList().get(0);
    }

    @Override
    public KnowledgeAreaTranslation findById(Long id, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        TypedQuery<KnowledgeAreaTranslation> query = getEntityManager().createNamedQuery("KnowledgeAreaTranslations.findByIdAndLanguage", KnowledgeAreaTranslation.class);
        query.setParameter("id", id);
        query.setParameter("language", language);

        return query.getResultList().get(0);
    }

}
