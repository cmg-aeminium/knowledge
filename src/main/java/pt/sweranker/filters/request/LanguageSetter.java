/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.filters.request;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import pt.sweranker.api.SwerankerApplication;
import pt.sweranker.persistence.Language;

/**
 * This is a global filter that checks every HTTP request for a language and if it is not sent
 * then it sets it to the default.
 * 
 * @author Carlos Gonçalves
 */
public class LanguageSetter implements ContainerRequestFilter {

    public static final String DEFAULT_LANGUAGE = Language.DEFAULT_LANGUAGE.toString();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String requestLanguage = requestContext.getHeaders().getFirst(SwerankerApplication.REQUEST_HEADER_LANGUAGE);

        if (requestLanguage == null || requestLanguage.isEmpty()) {
            requestContext.getHeaders().add(SwerankerApplication.REQUEST_HEADER_LANGUAGE, DEFAULT_LANGUAGE);
        }
    }

}
