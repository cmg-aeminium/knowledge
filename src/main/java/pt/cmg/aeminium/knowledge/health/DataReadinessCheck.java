/**
 * Copyright (c) 2020  Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.aeminium.knowledge.health;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import pt.cmg.aeminium.knowledge.dao.knowledgeareas.KnowledgeAreaDAO;

/**
 * @author Carlos Gonçalves
 */
@Readiness
@ApplicationScoped
public class DataReadinessCheck implements HealthCheck {

    @EJB
    private KnowledgeAreaDAO knowledgeAreaDAO;

    @Override
    public HealthCheckResponse call() {
        if (knowledgeAreaDAO.count() == 16L) {
            return HealthCheckResponse.up("All Knowledge Areas loaded");
        }

        return HealthCheckResponse.down("Missing Knowleadge Areas");
    }

}
