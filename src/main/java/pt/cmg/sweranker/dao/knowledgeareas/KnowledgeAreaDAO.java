package pt.cmg.sweranker.dao.knowledgeareas;

import javax.ejb.Stateless;
import pt.cmg.sweranker.dao.JPACrudDAO;
import pt.cmg.sweranker.persistence.entities.knowledgebodies.KnowledgeArea;

@Stateless
public class KnowledgeAreaDAO extends JPACrudDAO<KnowledgeArea> {

    public KnowledgeAreaDAO() {
        super(KnowledgeArea.class);
    }

}
