
package com.github.mule.google.calendar.connector.config;

import javax.annotation.Generated;
import com.github.mule.google.calendar.connector.connectivity.CalendarConnectorConnectionManager;
import org.mule.config.PoolingProfile;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class CalendarConnectorConfigDefinitionParser
    extends AbstractDefinitionParser
{


    public BeanDefinition parse(Element element, ParserContext parserContext) {
        parseConfigName(element);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(CalendarConnectorConnectionManager.class.getName());
        builder.setScope(BeanDefinition.SCOPE_SINGLETON);
        setInitMethodIfNeeded(builder, CalendarConnectorConnectionManager.class);
        setDestroyMethodIfNeeded(builder, CalendarConnectorConnectionManager.class);
        parseProperty(builder, element, "username", "username");
        parseProperty(builder, element, "password", "password");
        BeanDefinitionBuilder connectionPoolingProfileBuilder = BeanDefinitionBuilder.rootBeanDefinition(PoolingProfile.class.getName());
        Element connectionPoolingProfileElement = DomUtils.getChildElementByTagName(element, "connection-pooling-profile");
        if (connectionPoolingProfileElement!= null) {
            parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "maxActive");
            parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "maxIdle");
            parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "maxWait");
            if (hasAttribute(connectionPoolingProfileElement, "exhaustedAction")) {
                connectionPoolingProfileBuilder.addPropertyValue("exhaustedAction", PoolingProfile.POOL_EXHAUSTED_ACTIONS.get(connectionPoolingProfileElement.getAttribute("exhaustedAction")));
            }
            if (hasAttribute(connectionPoolingProfileElement, "initialisationPolicy")) {
                connectionPoolingProfileBuilder.addPropertyValue("initialisationPolicy", PoolingProfile.POOL_INITIALISATION_POLICIES.get(connectionPoolingProfileElement.getAttribute("initialisationPolicy")));
            }
            if (hasAttribute(connectionPoolingProfileElement, "evictionCheckIntervalMillis")) {
                parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "evictionCheckIntervalMillis");
            }
            if (hasAttribute(connectionPoolingProfileElement, "minEvictionMillis")) {
                parseProperty(connectionPoolingProfileBuilder, connectionPoolingProfileElement, "minEvictionMillis");
            }
            builder.addPropertyValue("connectionPoolingProfile", connectionPoolingProfileBuilder.getBeanDefinition());
        }
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        parseRetryPolicyTemplate("reconnect", element, parserContext, builder, definition);
        parseRetryPolicyTemplate("reconnect-forever", element, parserContext, builder, definition);
        parseRetryPolicyTemplate("reconnect-custom-strategy", element, parserContext, builder, definition);
        return definition;
    }

}
