/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.tasks.localisation;

import java.util.Objects;
import pt.cmg.aeminium.datamodel.common.entities.localisation.Language;

/**
 * @author Carlos Gonçalves
 */
public class LocalisedTextDTO {

    public Language language;
    public String value;

    @Override
    public int hashCode() {
        return Objects.hash(language);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LocalisedTextDTO other = (LocalisedTextDTO) obj;
        return language == other.language;
    }
}
