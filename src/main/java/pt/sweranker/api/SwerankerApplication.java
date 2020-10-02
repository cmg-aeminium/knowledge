package pt.sweranker.api;

import java.util.HashSet;
import java.util.Set;
import javax.json.bind.Jsonb;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.ContextResolver;
import pt.sweranker.api.resources.degrees.DegreeResource;
import pt.sweranker.api.resources.knowledgeareas.KnowledgeAreaResource;
import pt.sweranker.config.jsonb.JSONBConfigurator;
import pt.sweranker.filters.request.ApplicationDataRequestFilter;
import pt.sweranker.filters.request.LanguageSetterRequestFilter;

/**
 * @author Carlos Manuel
 */
@ApplicationPath("/v1")
public class SwerankerApplication extends Application {

    public static final String REQUEST_HEADER_LANGUAGE = "swr-language";
    public static final String REQUEST_HEADER_APP_NAME = "swr-app";
    public static final String REQUEST_HEADER_APP_VERSION = "swr-app-version";

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(KnowledgeAreaResource.class);
        resources.add(DegreeResource.class);

        resources.add(LanguageSetterRequestFilter.class);
        resources.add(ApplicationDataRequestFilter.class);
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();

        ContextResolver<Jsonb> jsonbConfiguration = new JSONBConfigurator();

        singletons.add(jsonbConfiguration);
        return singletons;
    }

}
