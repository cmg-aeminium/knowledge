/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.dao.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pt.cmg.sweranker.persistence.entities.localisation.Language;
import pt.cmg.sweranker.persistence.entities.localisation.TranslatedText;

/**
 * @author Carlos Gonçalves
 */
@ApplicationScoped
public class HazelcastCache {

    private static final Logger LOGGER = Logger.getLogger(HazelcastCache.class.getName());

    private static final String DEFAULT_LANG_MAP_NAME = "default_translations";
    private static final String ENGLISH_MAP_NAME = "english_translations";

    @Inject
    private HazelcastInstance hazelcast;

    private IMap<Long, String> defaultTexts;
    private IMap<Long, TranslatedText> translatedTexts;

    @PostConstruct
    public void initTranslationMap() {
        defaultTexts = hazelcast.getMap(DEFAULT_LANG_MAP_NAME);
        translatedTexts = hazelcast.getMap(ENGLISH_MAP_NAME);
    }

    public void putDefaultText(Long id, String textContent) {
        defaultTexts.putIfAbsent(id, textContent);
    }

    public void putTranslatedText(Long id, TranslatedText textContent) {
        translatedTexts.putIfAbsent(id, textContent);
    }

    public String getDefaultText(Long id) {
        return defaultTexts.get(id);
    }

    public String getTranslatedText(Long id, Language language) {
        SqlResult result = hazelcast.getSql().execute("SELECT textValue FROM english_translations WHERE __key = 5616L");
        for (SqlRow row : result) {
            String name = row.getObject(0);
        }

        return "Sim";
    }

}
