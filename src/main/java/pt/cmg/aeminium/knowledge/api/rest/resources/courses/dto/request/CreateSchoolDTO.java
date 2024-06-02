/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.tasks.schools;

import java.util.HashSet;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.Language;

/**
 * @author Carlos Gonçaalves
 */
public class CreateSchoolDTO {

    @NotNull(message = "Country cannot be null")
    public Long country;

    @NotEmpty(message = "No names were written")
    public HashSet<TranslatedName> names;

    public static class TranslatedName {
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
            TranslatedName other = (TranslatedName) obj;
            return language == other.language;
        }

    }

}
