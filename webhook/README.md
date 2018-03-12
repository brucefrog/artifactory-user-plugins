Artifactory Webhook Plugin
===============================

This plugin provides a webhook post to a remote service.  The plugin consists of 3 components:
1. webhook.groovy - the actual artifactory plugin, to be deployed in ARTIFACTORY_HOME/etc/plugins
2. webhook.properties - configuration file for setting url and events for activation
3. feedme.js - a node.js script for testing, logging http post

Copyright &copy; 2011-, JFrog Ltd.

