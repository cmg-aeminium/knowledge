/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.persistence.entities.degrees;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Carlos Gonçalves
 */
public enum University {

    U_PORTO,
    U_MINHO,
    U_COIMBRA,
    IST,
    U_AVEIRO;

    private static final Map<String, University> map = new HashMap<>();

    static {
        University[] values = University.values();
        for (University value : values) {
            map.put(value.name().toLowerCase(Locale.ROOT), value);
        }
    }

    public static University fromString(String name) {

        return map.get(name.toLowerCase(Locale.ROOT));
    }

}
