package pt.cmg.aeminium.knowledge.api.rest;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.ContextResolver;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.auth.LoginConfig;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.ApplicationDataRequestFilter;
import pt.cmg.aeminium.knowledge.api.rest.filters.request.LanguageSetterRequestFilter;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.CourseResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.courses.SchoolResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.KnowledgeAreaResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.knowledgebodies.KnowledgeBodyResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.login.LoginResource;
import pt.cmg.aeminium.knowledge.api.rest.resources.users.UserResource;
import pt.cmg.aeminium.knowledge.configuration.jsonb.JsonbProvider;
import pt.cmg.aeminium.knowledge.persistence.entities.localisation.Language;

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

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 32;

    // The maximum email length is 254, by the specification RFC 5321 (see http://www.dominicsayers.com/isemail/).
    // This is, however a source of a heated discussion, so bear that in mind should this blow in the future
    public static final int EMAIL_MAX_LENGTH = 254;

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        resources.add(KnowledgeBodyResource.class);
        resources.add(KnowledgeAreaResource.class);
        resources.add(CourseResource.class);
        resources.add(SchoolResource.class);
        resources.add(UserResource.class);
        resources.add(LoginResource.class);

        resources.add(LanguageSetterRequestFilter.class);
        resources.add(ApplicationDataRequestFilter.class);
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();

        ContextResolver<Jsonb> jsonbConfiguration = new JsonbProvider();

        singletons.add(jsonbConfiguration);

        return singletons;
    }

    /**
     * Simple password rule enforcing.
     * Returns true if this password respects the basic rules enforced by FC, which are:<br>
     * 1) it must have 6 or more characters<br>
     * 2) it must have 32 or less characters<br>
     * 3) it must not be composed entirely by whitespaces<br>
     * 4) it must be composed only by letters or numbers (a-z, A-Z, 0-9)
     *
     * TODO: should this be here? So far it seems an acceptable temporary place
     */
    public static boolean isAcceptablePassword(String password, boolean onlyAcceptAlphanumeric) {
        if (StringUtils.isBlank(password) ||
            password.length() < KnowledgeApplication.PASSWORD_MIN_LENGTH ||
            password.length() > KnowledgeApplication.PASSWORD_MAX_LENGTH) {
            return false;
        }
        if (StringUtils.containsOnly(password, ' ')) {
            return false;
        }
        if (onlyAcceptAlphanumeric && !StringUtils.isAlphanumericSpace(password)) {
            return false;
        }
        return true;
    }
}
