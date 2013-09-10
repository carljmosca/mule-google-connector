
package com.github.mule.google.calendar.connector.process;

import java.util.List;
import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.adapters.CalendarConnectorConnectionIdentifierAdapter;
import com.github.mule.google.calendar.connector.connection.ConnectionManager;
import com.github.mule.google.calendar.connector.connection.UnableToAcquireConnectionException;
import com.github.mule.google.calendar.connector.connection.UnableToReleaseConnectionException;
import com.github.mule.google.calendar.connector.connectivity.CalendarConnectorConnectionKey;
import com.github.mule.google.calendar.connector.processors.AbstractConnectedProcessor;
import com.github.mule.google.calendar.connector.processors.AbstractExpressionEvaluator;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class ManagedConnectionProcessInterceptor<T >
    extends AbstractExpressionEvaluator
    implements ProcessInterceptor<T, CalendarConnectorConnectionIdentifierAdapter>
{

    private static Logger logger = LoggerFactory.getLogger(ManagedConnectionProcessInterceptor.class);
    private final ConnectionManager<CalendarConnectorConnectionKey, CalendarConnectorConnectionIdentifierAdapter> connectionManager;
    private final MuleContext muleContext;
    private final ProcessInterceptor<T, CalendarConnectorConnectionIdentifierAdapter> next;

    public ManagedConnectionProcessInterceptor(ProcessInterceptor<T, CalendarConnectorConnectionIdentifierAdapter> next, ConnectionManager<CalendarConnectorConnectionKey, CalendarConnectorConnectionIdentifierAdapter> connectionManager, MuleContext muleContext) {
        this.next = next;
        this.connectionManager = connectionManager;
        this.muleContext = muleContext;
    }

    public T execute(ProcessCallback<T, CalendarConnectorConnectionIdentifierAdapter> processCallback, CalendarConnectorConnectionIdentifierAdapter object, MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        CalendarConnectorConnectionIdentifierAdapter connection = null;
        CalendarConnectorConnectionKey key = null;
        if ((messageProcessor!= null)&&((messageProcessor instanceof AbstractConnectedProcessor)&&(((AbstractConnectedProcessor) messageProcessor).getUsername()!= null))) {
            final String _transformedUsername = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_usernameType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getUsername()));
            if (_transformedUsername == null) {
                throw new UnableToAcquireConnectionException("Parameter username in method connect can't be null because is not @Optional");
            }
            final String _transformedPassword = ((String) evaluateAndTransform(muleContext, event, AbstractConnectedProcessor.class.getDeclaredField("_passwordType").getGenericType(), null, ((AbstractConnectedProcessor) messageProcessor).getPassword()));
            if (_transformedPassword == null) {
                throw new UnableToAcquireConnectionException("Parameter password in method connect can't be null because is not @Optional");
            }
            key = new CalendarConnectorConnectionKey(_transformedUsername, _transformedPassword);
        } else {
            key = connectionManager.getDefaultConnectionKey();
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(("Attempting to acquire connection using "+ key.toString()));
            }
            connection = connectionManager.acquireConnection(key);
            if (connection == null) {
                throw new UnableToAcquireConnectionException();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug((("Connection has been acquired with [id="+ connection.getConnectionIdentifier())+"]"));
                }
            }
            return next.execute(processCallback, connection, messageProcessor, event);
        } catch (Exception e) {
            if (processCallback.getManagedExceptions()!= null) {
                for (Class exceptionClass: ((List<Class> ) processCallback.getManagedExceptions())) {
                    if (exceptionClass.isInstance(e)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug((((("An exception ( "+ exceptionClass.getName())+") has been thrown. Destroying the connection with [id=")+ connection.getConnectionIdentifier())+"]"));
                        }
                        try {
                            connectionManager.destroyConnection(key, connection);
                            connection = null;
                        } catch (Exception innerException) {
                            logger.error(innerException.getMessage(), innerException);
                        }
                    }
                }
            }
            throw e;
        } finally {
            try {
                if (connection!= null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug((("Releasing the connection back into the pool [id="+ connection.getConnectionIdentifier())+"]"));
                    }
                    connectionManager.releaseConnection(key, connection);
                }
            } catch (Exception e) {
                throw new UnableToReleaseConnectionException(e);
            }
        }
    }

    public T execute(ProcessCallback<T, CalendarConnectorConnectionIdentifierAdapter> processCallback, CalendarConnectorConnectionIdentifierAdapter object, Filter filter, MuleMessage message)
        throws Exception
    {
        throw new UnsupportedOperationException();
    }

}
