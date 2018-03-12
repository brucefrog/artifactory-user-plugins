Artifactory Webhook Plugin
===============================

This plugin provides a webhook post to a remote service.  The plugin consists of 3 components:
1. webhook.groovy - the actual artifactory plugin
2. webhook.properties - configuration file for setting url and events for activation
3. feedme.js - a node.js script for testing, logging http post

All Artifactory supported events should already be intercepted by webhook.groovy script.  If an event is activated in webhook.properties, then the event will invoke the desigated webhook url.

Installation
-----------------
1. Configure webhook.properties and copy to ARTIFACTORY_HOME/etc/plugins
2. Copy webhook.groovy to ARTIFACTORY_HOME/etc/plugins

Copyright &copy; 2011-, JFrog Ltd.

