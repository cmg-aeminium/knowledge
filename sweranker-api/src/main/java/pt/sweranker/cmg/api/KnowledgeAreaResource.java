package pt.sweranker.cmg.api;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pt.sweranker.cmg.dao.knowledgeareas.KnowledgeAreaDAO;
import pt.sweranker.cmg.persistence.knowledgeareas.KnowledgeAreaTranslation;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("ka")
@Stateless
public class KnowledgeAreaResource {

    @EJB
    private KnowledgeAreaDAO knowledgeAreaDAO;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {

        KnowledgeAreaTranslation ka = knowledgeAreaDAO.findById(1L, null);

        return ka.getTranslatedName() + " - " + ka.getTranslatedDescription();
    }
}
