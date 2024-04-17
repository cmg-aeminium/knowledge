/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.dao.knowledgeareas;

import javax.ejb.Stateless;
import pt.cmg.sweranker.dao.JPACrudDAO;
import pt.cmg.sweranker.persistence.entities.knowledgebodies.KnowledgeBody;

@Stateless
public class KnowledgeBodyDAO extends JPACrudDAO<KnowledgeBody> {

    public KnowledgeBodyDAO() {
        super(KnowledgeBody.class);
    }

}
