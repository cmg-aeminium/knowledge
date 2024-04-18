/**
 * Copyright (c) 2019 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.dao.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.persistence.jpa.JpaCache;
import pt.cmg.jakartautils.jpa.QueryUtils;
import pt.cmg.sweranker.persistence.entities.Language;
import pt.cmg.sweranker.persistence.entities.localisation.TextContent;
import pt.cmg.sweranker.persistence.entities.localisation.TranslatedText;
import pt.cmg.sweranker.persistence.entities.schools.School;

/**
 * @author Carlos Gonçalves
 */
@Singleton
@Startup
public class CacheLoader {

    private static final Logger LOGGER = Logger.getLogger(CacheLoader.class.getName());

    @PersistenceUnit(unitName = "sweranker-data")
    private EntityManagerFactory entityManagerFactory;

    // This variable can be injected into the environment to control whether it should run or not
    @Inject
    @ConfigProperty(name = "jpa.cache.loadAtStartup", defaultValue = "true")
    private boolean loadCacheAtStartup;

    // @Inject
    // @ConfigProperty(name = "jpa.cache.refresh", defaultValue = "true")
    // private boolean refreshCacheEnabled;

    @Inject
    @ConfigProperty(name = "app.language", defaultValue = Language.DEFAULT_LANGUAGE_NAME)
    private Language language;

    @PostConstruct
    public void loadCacheAtStartup() {

        if (loadCacheAtStartup) {
            LOGGER.info("Loading Cache at startup is active. Loading objects and query results to memory.");
            loadObjectCache();
        } else {
            LOGGER.warning("No objects/queries will be loaded at startup, but cache will still be available whenever objects are loaded from database.");
        }

    }

    /**
     * Triggered somewhere to refresh cache
     */
    // public void refreshCacheTimer() {
    // if (refreshCacheEnabled) {
    // refreshCache();
    // }
    // }

    @Asynchronous
    public void refreshCache() {
        invalidateAllCaches();
        loadObjectCache();
    }

    /**
     * This will clear the shared cache of ALL data.
     * Either OBJECT cache and QUERY RESULTS cache.
     * If used, please reload all of it again.
     * I can't think of a good reason to use this alone, but maybe for sanity check purposes.
     */
    public void invalidateAllCaches() {

        Cache appCache = entityManagerFactory.getCache();
        appCache.evictAll();

        if (appCache instanceof JpaCache) {
            ((JpaCache) appCache).clearQueryCache();
        }

    }

    /**
     * Loads all the object cache.
     * This affects only objects that are accessed by their ID and nothing else.
     */
    private void loadObjectCache() {
        LOGGER.log(Level.INFO, "Started loading Object cache");
        loadSchools();
        LOGGER.log(Level.INFO, "Finished loading Object cache");
    }

    public void loadSchools() {

        EntityManager database = entityManagerFactory.createEntityManager();

        TypedQuery<School> query = database.createNamedQuery(School.QUERY_FIND_ALL, School.class);
        var schools = query.getResultList();

        Set<Long> ids = schools.stream().map(School::getNameTextContentId).collect(Collectors.toSet());

        Map<Long, String> textualValues = getTextValuesById(ids, language);

        for (School school : schools) {
            school.setName(textualValues.get(school.getNameTextContentId()));
        }

        database.close();

    }

    /**
     * Returns the textual translations for the given IDs.<br>
     * It returns map where the keys are the translated text ids and the values are the textual values for that language.
     * 
     * NOTE: this is a good example of how to load data from the database without turning it into an object, by taking advantage of
     * the ArrayRecord class from Eclipselink.
     * This way, it loads an ArrayRecord which I can then use to create an object
     */
    private Map<Long, String> getTextValuesById(Collection<Long> textIds, Language language) {

        if (textIds == null || textIds.isEmpty()) {
            return Collections.emptyMap();
        }

        EntityManager database = entityManagerFactory.createEntityManager();

        Query query;

        if (language == Language.DEFAULT_LANGUAGE) {
            query = database.createNamedQuery(TextContent.QUERY_FIND_IN_IDS);
            query.setParameter("ids", textIds);
        } else {
            query = database.createNamedQuery(TranslatedText.QUERY_FIND_IN_IDS);
            query.setParameter("ids", textIds);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> result = QueryUtils.getResultListFromQuery(query);

        return result
            .stream()
            .collect(
                Collectors.toMap(
                    arrayRow -> (Long) arrayRow[0],
                    arrayRow -> (String) arrayRow[1]));

    }

}
