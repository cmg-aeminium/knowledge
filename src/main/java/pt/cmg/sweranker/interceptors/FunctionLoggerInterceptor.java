/*
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.sweranker.interceptors;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import pt.cmg.jakartautils.text.TextFormatter;
import pt.cmg.jakartautils.time.DateTimeUtil;
import pt.cmg.sweranker.dao.logging.FunctionLogDAO;
import pt.cmg.sweranker.persistence.entities.logging.FunctionLog;

/**
 * @author Carlos Gonçalves
 */
@Interceptor
@FunctionLogger
@Priority(Interceptor.Priority.APPLICATION + 5)
public class FunctionLoggerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(FunctionLoggerInterceptor.class.getSimpleName());

    @Inject
    private FunctionLogDAO functionLogDAO;

    @AroundInvoke
    public Object logFunctionResult(InvocationContext invocationContext) throws Exception {

        // Should only be applied to functions
        // However, the annotation is available for Types (because it is a Qualifier).
        // So we ignore it if is not called for a function
        if (invocationContext.getMethod() == null) {
            return invocationContext.proceed();
        }

        FunctionLog functionLog = new FunctionLog();
        functionLog.setFunctionName(getFunctionAnnotationNameAttribute(invocationContext));

        LocalDateTime startTime = LocalDateTime.now();
        functionLog.setStartedAt(startTime);

        Object invocationResult = null;
        try {

            invocationResult = invocationContext.proceed();

            LocalDateTime endTime = LocalDateTime.now();
            functionLog.setFinishedAt(endTime);
            functionLog.setExecutionTimeInMillis(DateTimeUtil.dateDiffInMilliseconds(endTime, startTime));
            functionLog.setWasSuccess(true);

        } catch (Exception e) {

            LocalDateTime endTime = LocalDateTime.now();
            functionLog.setFinishedAt(endTime);
            functionLog.setExecutionTimeInMillis(DateTimeUtil.dateDiffInMilliseconds(endTime, startTime));
            functionLog.setWasSuccess(false);
            functionLog.setExceptionInfo(e);
        }

        functionLogDAO.create(functionLog);

        LOGGER.info(TextFormatter.formatMessageToLazyLog("Function {0} ended with status {1}, total time {2}", functionLog.getFunctionName(),
            functionLog.wasSuccess() ? "SUCCESS" : "FAIL", functionLog.getExecutionTimeInMillis()));

        return invocationResult;
    }

    // USAGE ON A FUNCTION OF AN EJB : @FunctionLogger(name = "name of function")

    private String getFunctionAnnotationNameAttribute(InvocationContext ctx) {

        FunctionLogger classAnnotation = ctx.getMethod().getAnnotation(FunctionLogger.class);

        if (classAnnotation != null) {
            return classAnnotation.name();
        }

        return FunctionLogger.DEFAULT_NAME;
    }

}
