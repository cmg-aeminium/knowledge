/*
 * Copyright (c) 2019  Fashion Concierge
 * All rights reserved.
 */
package pt.sweranker.cmg.api;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

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
