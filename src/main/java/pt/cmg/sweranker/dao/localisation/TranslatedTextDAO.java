/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.dao.localisation;

import javax.ejb.Stateless;
import pt.cmg.sweranker.dao.JPACrudDAO;
import pt.cmg.sweranker.persistence.entities.localisation.TranslatedText;

/**
 * @author Carlos Gonçalves
 */
@Stateless
public class TranslatedTextDAO extends JPACrudDAO<TranslatedText> {

    public TranslatedTextDAO() {
        super(TranslatedText.class);
    }

}
