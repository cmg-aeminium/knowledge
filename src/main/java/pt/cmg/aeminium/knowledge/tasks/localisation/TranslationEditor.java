/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.tasks.localisation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import pt.cmg.aeminium.knowledge.cache.HazelcastCache;
import pt.cmg.aeminium.knowledge.dao.localisation.TextContentDAO;
import pt.cmg.aeminium.knowledge.dao.localisation.TranslatedTextDAO;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.Language;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.TextContent;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.TranslatedText;

/**
 * @author Carlos Gonçalves
 */
@Singleton
public class TranslationEditor {

    @Inject
    private TextContentDAO textContentDAO;

    @Inject
    private TranslatedTextDAO translatedTextDAO;

    @Inject
    private HazelcastCache textCache;

    public TextContent createTranslatedTexts(Collection<LocalisedTextDTO> translations) {

        if (translations == null || translations.isEmpty()) {
            TextContent originalText = TextContent.createEmptyTextContent();
            textContentDAO.create(originalText);
            return originalText;
        }

        TextContent originalText = null;
        Set<TranslatedText> translatedTexts = new HashSet<>();

        for (var localisedText : translations) {

            if (localisedText.language == Language.DEFAULT_LANGUAGE) {
                originalText = new TextContent(Language.DEFAULT_LANGUAGE, localisedText.value);
            } else {
                TranslatedText translation = new TranslatedText(localisedText.language, localisedText.value);
                translatedTexts.add(translation);
            }
        }

        if (originalText == null) {
            originalText = TextContent.createEmptyTextContent();
        }

        textContentDAO.create(originalText, true);
        textCache.putTranslation(originalText);

        for (var translation : translatedTexts) {

            translation.setId(originalText.getId());

            translatedTextDAO.create(translation);
            textCache.putTranslation(translation);
        }

        return originalText;
    }

    public void updateTraslatedTexts(Long translationId, Collection<LocalisedTextDTO> translations) {

        if (translations == null || translations.isEmpty()) {
            return;
        }

        for (var localisation : translations) {

            if (localisation.language == Language.DEFAULT_LANGUAGE) {
                TextContent textContent = textContentDAO.findById(translationId);
                textContent.setTextValue(localisation.value);
                textCache.replaceTranslation(textContent);
            } else {
                TranslatedText textContent = translatedTextDAO.findById(translationId);
                textContent.setTextValue(localisation.value);
                textCache.replaceTranslation(textContent);
            }

        }
    }

}
