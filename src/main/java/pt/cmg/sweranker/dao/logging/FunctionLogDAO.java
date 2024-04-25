/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.dao.logging;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import pt.cmg.jakartautils.jpa.QueryUtils;
import pt.cmg.sweranker.dao.JPACrudDAO;
import pt.cmg.sweranker.persistence.entities.logging.FunctionLog;

@Stateless
public class FunctionLogDAO extends JPACrudDAO<FunctionLog> {

    public FunctionLogDAO() {
        super(FunctionLog.class);
    }

    public FunctionLog getLatestLogForFunction(String className) {

        if (StringUtils.isBlank(className)) {
            return null;
        }

        TypedQuery<FunctionLog> query = getEntityManager().createNamedQuery(FunctionLog.QUERY_FIND_LATEST_FOR_FUNCTION, FunctionLog.class);
        query.setParameter(1, className);

        List<FunctionLog> resultList = QueryUtils.getResultListFromQuery(query);
        return resultList.isEmpty() ? null : resultList.get(0);
    }

}
