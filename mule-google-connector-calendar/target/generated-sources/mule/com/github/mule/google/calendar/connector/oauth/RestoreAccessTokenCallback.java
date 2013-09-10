
package com.github.mule.google.calendar.connector.oauth;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-09-10T03:24:40-04:00", comments = "Build 3.4.0.1555.8df15c1")
public interface RestoreAccessTokenCallback {

     /**
     * Restore access token and secret
     */
    void restoreAccessToken();

    /**
     * Retrieve the just restored access token
     *
     * @return A string representing the access token
     */
    String getAccessToken();

    /**
     * Retrieve the access token secret
     *
     * @return A string representing the access token secret
     */
    String getAccessTokenSecret();
}
