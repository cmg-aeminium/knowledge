package pt.sweranker.cmg.dao.knowledgeareas;

import pt.sweranker.cmg.persistence.Language;
import pt.sweranker.cmg.persistence.knowledgeareas.KnowledgeAreaTranslation;

public interface KnowledgeAreaDAO {

    /**
     * Fetches a KnowledgeAreaTranslation by its id and for a given language, returning a default if it didn't find any result of the
     * required language
     * 
     * @param id
     * @param language
     * @return
     */
    KnowledgeAreaTranslation findById(Long id, Language language);

}
