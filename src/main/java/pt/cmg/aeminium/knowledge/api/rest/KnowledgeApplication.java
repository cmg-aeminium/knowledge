package pt.cmg.aeminium.knowledge.api.rest;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.microprofile.auth.LoginConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import pt.cmg.aeminium.datamodel.common.entities.localisation.Language;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.ApplicationDataRequestFilter;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.LanguageSetterRequestFilter;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.UserLoaderRequestFilter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.CourseClassResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.CourseResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.SchoolResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.KnowledgeAreaResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.KnowledgeBodyResource;
import pt.cmg.aeminium.knowledge.configuration.jsonb.JsonbProvider;
import pt.cmg.jakartautils.errors.ConstraintViolationExceptionMapper;

/**
 * @author Carlos Gonçalves
 */
@ApplicationScoped
@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("/v1")
public class KnowledgeApplication extends Application {

    public static final String REQUEST_HEADER_LANGUAGE = "aem-language";
    public static final String REQUEST_HEADER_APP_NAME = "aem-app";
    public static final String REQUEST_HEADER_APP_VERSION = "aem-app-version";

    public static final Language APP_DEFAULT_LANGUAGE = Language.DEFAULT_LANGUAGE;

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(KnowledgeBodyResource.class);
        resources.add(KnowledgeAreaResource.class);
        resources.add(CourseResource.class);
        resources.add(CourseClassResource.class);
        resources.add(SchoolResource.class);

        resources.add(LanguageSetterRequestFilter.class);
        resources.add(ApplicationDataRequestFilter.class);
        resources.add(UserLoaderRequestFilter.class);

        resources.add(JsonbProvider.class);
        resources.add(ConstraintViolationExceptionMapper.class);
        return resources;
    }

    // @Override
    // public Set<Object> getSingletons() {
    // Set<Object> singletons = new HashSet<>();
    //
    // ContextResolver<Jsonb> jsonbConfiguration = new JsonbProvider();
    //
    // singletons.add(jsonbConfiguration);
    // singletons.add(new ConstraintViolationExceptionMapper());
    //
    // return singletons;
    // }

}
