/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.sweranker.filters.request;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import pt.sweranker.persistence.entities.Language;

/**
 * @author Carlos Gonçalves
 */
@RequestScoped
public class RequestDataProducer {

    private RequestPayload rpd = new RequestPayload();

    public void observeLanguage(@Observes @RequestData Language language) {
        this.rpd.setSelectedLanguage(language);
    }

    public void observeAppData(@Observes @RequestData ClientApplicationData appData) {
        this.rpd.setAppData(appData);
    }

    @Produces
    @RequestScoped
    @RequestData
    public RequestPayload getKA() {
        return this.rpd;
    }

}