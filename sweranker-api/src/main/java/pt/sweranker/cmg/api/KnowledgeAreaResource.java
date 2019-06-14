package pt.sweranker.cmg.api;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@PathParam("id") Long id) {

        KnowledgeAreaTranslation ka = knowledgeAreaDAO.findByIdAndLanguage(id, null);

        return ka.getTranslatedName() + " - " + ka.getTranslatedDescription();
    }

    @GET
    @Path("/no/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String updateIt(@PathParam("id") Long id, @QueryParam("name") @DefaultValue("Meh") String newName) {

        KnowledgeAreaTranslation ka = knowledgeAreaDAO.findByIdAndLanguage(id, null);

        ka.getKnowledgeArea().setImage(newName);

        return ka.getTranslatedName() + " - " + ka.getTranslatedDescription();
    }
}
