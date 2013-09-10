
package com.github.mule.google.calendar.connector.processors;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public abstract class AbstractConnectedProcessor
    extends AbstractExpressionEvaluator
{

    protected Object username;
    protected String _usernameType;
    protected Object password;
    protected String _passwordType;

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(Object value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public Object getUsername() {
        return this.username;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(Object value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public Object getPassword() {
        return this.password;
    }

}
