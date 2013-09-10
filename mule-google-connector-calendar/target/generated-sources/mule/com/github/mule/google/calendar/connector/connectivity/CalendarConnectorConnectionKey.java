
package com.github.mule.google.calendar.connector.connectivity;

import javax.annotation.Generated;


/**
 * A tuple of connection parameters
 * 
 */
@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public class CalendarConnectorConnectionKey {

    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String password;

    public CalendarConnectorConnectionKey(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets password
     * 
     * @param value Value to set
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Retrieves password
     * 
     */
    public String getPassword() {
        return this.password;
    }

    public int hashCode() {
        int hash = 1;
        hash = (hash* 31);
        if (this.username!= null) {
            hash += this.username.hashCode();
        }
        return hash;
    }

    public boolean equals(Object obj) {
        return (((obj instanceof CalendarConnectorConnectionKey)&&(this.username!= null))&&this.username.equals(((CalendarConnectorConnectionKey) obj).username));
    }

}
