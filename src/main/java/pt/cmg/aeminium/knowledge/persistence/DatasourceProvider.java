package pt.cmg.aeminium.knowledge.persistence;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;

/**
 * @author Carlos Gonçalves
 */
@Singleton
@DataSourceDefinition(
    name = "java:global/KnowledgeDataSource",
    className = "${datasource.javaclass}",
    user = "${database.user}",
    password = "${database.password}",
    serverName = "${database.host}",
    portNumber = 5432, // port must always be an integer
    databaseName = "${database.name}",
    properties = {"stringtype=unspecified", "fish.payara.log-jdbc-calls=true"},
    initialPoolSize = 1,
    maxPoolSize = 5)
public class DatasourceProvider {
}
