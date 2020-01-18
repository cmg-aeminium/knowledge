/*
 * Copyright (c) 2019  Fashion Concierge
 * All rights reserved.
 */
package pt.sweranker.cmg.dao.cache;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import pt.sweranker.cmg.persistence.Language;
import pt.sweranker.cmg.persistence.knowledgeareas.KnowledgeAreaTranslation;

/**
 * @author Carlos Manuel
 */
@Singleton
@Startup
public class CacheLoader {

    @PersistenceUnit(unitName = "sweranker-data")
    private EntityManagerFactory entitiyManagerFactory;

    @PostConstruct
    public void loadCache() {

        EntityManager database = entitiyManagerFactory.createEntityManager();

        for (long i = 1; i <= 15; i++) {
            TypedQuery<KnowledgeAreaTranslation> query = database.createNamedQuery("KnowledgeAreaTranslations.findByIdAndLanguage", KnowledgeAreaTranslation.class);
            query.setParameter("id", i);
            query.setParameter("language", Language.PT_PT);

            query.getResultList();
        }

        database.close();

    }

}
