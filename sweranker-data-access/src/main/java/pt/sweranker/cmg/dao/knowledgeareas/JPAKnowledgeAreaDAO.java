package pt.sweranker.cmg.dao.knowledgeareas;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pt.sweranker.cmg.dao.JPACrudDAO;
import pt.sweranker.cmg.persistence.Language;
import pt.sweranker.cmg.persistence.knowledgeareas.KnowledgeArea;
import pt.sweranker.cmg.persistence.knowledgeareas.KnowledgeAreaTranslation;
import pt.sweranker.cmg.persistence.knowledgeareas.KnowledgeAreaTranslation_;

@Stateless
public class JPAKnowledgeAreaDAO extends JPACrudDAO<KnowledgeArea> implements KnowledgeAreaDAO {

    public JPAKnowledgeAreaDAO() {
        super(KnowledgeArea.class);
    }

    @Override
    public KnowledgeAreaTranslation findById(Long id, Language language) {

        if (language == null) {
            language = Language.DEFAULT_LANGUAGE;
        }

        CriteriaBuilder criteria = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<KnowledgeAreaTranslation> query = criteria.createQuery(KnowledgeAreaTranslation.class);

        Root<KnowledgeAreaTranslation> knowledgeAreas = query.from(KnowledgeAreaTranslation.class);

        List<Predicate> whereClauses = new ArrayList<>();
        whereClauses.add(criteria.equal(knowledgeAreas.get(KnowledgeAreaTranslation_.language), language));

        query.select(knowledgeAreas)
            .where(whereClauses.toArray(new Predicate[whereClauses.size()]));

        return getEntityManager().createQuery(query).getResultList().get(0);
    }

}
