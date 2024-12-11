/**
 * Copyright (c) 2020 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.cmg.aeminium.knowledge.cache.ObjectCacheLoader;

/**
 * @author Carlos Gonçalves
 */
@Readiness
@ApplicationScoped
public class DataReadinessCheck implements HealthCheck {

    @Inject
    private ObjectCacheLoader objectCache;

    @Override
    public HealthCheckResponse call() {
        if (objectCache.isCacheReady()) {
            return HealthCheckResponse.up("knowledge");
        }

        return HealthCheckResponse.down("knowledge");
    }

}
