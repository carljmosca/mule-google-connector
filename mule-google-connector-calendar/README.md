
WELCOME
=======

This Mule Connector was initially created with the MuleSoft DevKit wizard and is intended for access to Google calendars.  While there is a publicly available suite of connectors which provide a rich set of features, we could not find a working example and could not make the connector work with our versions of MuleESB and Google configuration.

The goal was to create a simple, working example along with a test flow.

./pom.xml:
A maven project descriptor that describes how to build this module.

./LICENSE.md:
The open source license text for this project.

BUILDING
========

from the command line: mvn clean package -Ddevkit.studio.package.skip=false

if you don't have a Google account configured and need or want to build without testing: mvn clean package -Ddevkit.studio.package.skip=false -DskipTests

TESTING
=======

This  project also contains test classes that can be run as part of a test
suite.

CONFIGURATION
=============

The client_secrets.json file (download from Google after creating an OAuth2 Client ID for API Access) should be placed in USER_HOME/.store

Go to the API Console: https://code.google.com/apis/console

If no projects exist, click the Create Project button
	
Next, enable the Calendar API via APIs and Auth -> APIs -> Calendar API 


ADDITIONAL RESOURCES
====================
Everything you need to know about getting started with Mule can be found here:
http://www.mulesoft.org/documentation/display/MULE3INTRO/Home

There further useful information about extending Mule here:
http://www.mulesoft.org/documentation/display/DEVKIT/Home

Remember if you get stuck you can try getting help on the Mule user list:
http://www.mulesoft.org/email-lists

Also, MuleSoft, the company behind Mule, offers 24x7 support options:
http://www.mulesoft.com/enterprise-subscriptions-and-support

Enjoy your Mule ride!

The Mule Team

