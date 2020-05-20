package pt.sweranker.config.datasource;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

/**
 * @author Carlos Gonçalves
 */
@Singleton
@DataSourceDefinition(
    name = "java:global/SwerankerDataSource",
    className = "${datasource.javaclass}",
    user = "${database.user}",
    password = "${database.password}",
    serverName = "${database.host}",
    portNumber = 5432, //port must always be an integer
    databaseName = "${database.name}",
    properties = {"stringtype=unspecified", "fish.payara.log-jdbc-calls=true"},
    initialPoolSize = 1,
    maxPoolSize = 5)
public class DatasourceProvider {

    @Resource(lookup = "java:global/SwerankerDataSource")
    private DataSource datasource;

    @Produces
    public DataSource getDatasource() {
        return datasource;
    }

}
