package pt.sweranker.cmg.dao;

import javax.ejb.Stateless;
import pt.sweranker.cmg.persistence.KnowledgeArea;

@Stateless
public class JPAKnowledgeAreaDAO extends JPACrudDAO<KnowledgeArea> implements KnowledgeAreaDAO {

    public JPAKnowledgeAreaDAO() {
        super(KnowledgeArea.class);
    }

}
