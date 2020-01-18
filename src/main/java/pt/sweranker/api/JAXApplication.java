package pt.sweranker.api;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import pt.sweranker.api.resources.knowledgeareas.KnowledgeAreaResource;

/**
 * @author Carlos Manuel
 */
@ApplicationPath("/")
public class JAXApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(KnowledgeAreaResource.class);
        return resources;
    }

}
