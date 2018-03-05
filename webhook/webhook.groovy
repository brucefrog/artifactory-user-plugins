/*
 * Copyright (C) 2011 JFrog Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import groovy.json.JsonBuilder

/**
 * Webhook for Artifactory
 *
 * This webhook includes the following components
 * 1. webook.groovy - main script, modify only if needing to change functionality
 * 2. webhook.properties - specify the target url and event to trigger webhook
 * 3. feedme.js - a sample nodeJS server for logging all webhook rest api calls
 *
 * Installation:
 * 1. add webhook.properties to <artifactory.home>/etc/plugins folder, configure as needed
 * 2. add webook.groovy to <artifactory.home>/etc/plugins, artifactory should log: Script 'webhook' loaded.
 *
 *
 */


executions {

    /**
     * An execution definition.
     * The first value is a unique name for the execution.
     *
     * Context variables:
     * status (int) - a response status code. Defaults to -1 (unset). Not applicable for an async execution.
     * message (java.lang.String) - a text message to return in the response body, replacing the response content.
     *                              Defaults to null. Not applicable for an async execution.
     *
     * Plugin info annotation parameters:
     *  version (java.lang.String) - Closure version. Optional.
     *  description (java.lang.String) - Closure description. Optional.
     *  httpMethod (java.lang.String, values are GET|PUT|DELETE|POST) - HTTP method this closure is going
     *    to be invoked with. Optional (defaults to POST).
     *  params (java.util.Map<java.lang.String, java.lang.String>) - Closure default parameters. Optional.
     *  users (java.util.Set<java.lang.String>) - Users permitted to query this plugin for information or invoke it.
     *  groups (java.util.Set<java.lang.String>) - Groups permitted to query this plugin for information or invoke it.
     *
     * Closure parameters:
     *  params (java.util.Map) - An execution takes a read-only key-value map that corresponds to the REST request
     *    parameter 'params'. Each entry in the map contains an array of values. This is the default closure parameter,
     *    and so if not named it will be "it" in groovy.
     *  ResourceStreamHandle body - Enables you to access the full input stream of the request body.
     *    This will be considered only if the type ResourceStreamHandle is declared in the closure.
     */


    hitMe(httpMethod: 'GET', users:[], groups:[], params:[:]) {

        hook('execute.hitMe', it ? new JsonBuilder(it) : null)
    }

    hitMore(httpMethod: 'GET', users:[], groups:[], params:[:]) {

        hook('execute.hitMore', it ? new JsonBuilder(it) : null)
    }


    webhookReload (httpMethod: 'GET') {
        WebHook.reload()
    }

}


storage {

    /**
     * Handle before create events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the original item being created.
     */
    beforeCreate { item ->
        hook('storage.beforeCreate', item ? new JsonBuilder(item) : null)
    }

    /**
     * Handle after create events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the original item being created.
     */
    afterCreate { item ->
        hook('storage.afterCreate', item ? new JsonBuilder(item) : null)
    }

    /**
     * Handle before delete events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the original item being being deleted.
     */
    beforeDelete { item ->
        hook('storage.beforeDelete', item ? new JsonBuilder(item) : null)
    }

    /**
     * Handle after delete events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the original item deleted.
     */
    afterDelete { item ->
        hook('storage.afterDelete', item ? new JsonBuilder(item) : null)
    }

    /**
     * Handle before move events.
     *
     * Closure parameters:

     * item (org.artifactory.fs.ItemInfo) - the source item being moved.
     * targetRepoPath (org.artifactory.repo.RepoPath) - the target repoPath for the move.
     */
    beforeMove { item, targetRepoPath, properties ->
        def json = new JsonBuilder()
        json (
                item: item,
                targetRepoPath: targetRepoPath,
                properties: properties
        )
        hook('storage.beforeMove', json)
    }

    /**
     * Handle after move events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the source item moved.
     * targetRepoPath (org.artifactory.repo.RepoPath) - the target repoPath for the move.
     */
    afterMove { item, targetRepoPath, properties ->
        def json = new JsonBuilder()
        json (
                item: item,
                targetRepoPath: targetRepoPath,
                properties: properties
        )
        hook('storage.afterMove', json)
    }

    /**
     * Handle before copy events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the source item being copied.
     * targetRepoPath (org.artifactory.repo.RepoPath) - the target repoPath for the copy.
     */
    beforeCopy { item, targetRepoPath, properties ->
        def json = new JsonBuilder()
        json (
                item: item,
                targetRepoPath: targetRepoPath,
                properties: properties
        )
        hook('storage.beforeCopy', json)
    }

    /**
     * Handle after copy events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the source item copied.
     * targetRepoPath (org.artifactory.repo.RepoPath) - the target repoPath for the copy.
     */
    afterCopy { item, targetRepoPath, properties ->
        def json = new JsonBuilder()
        json (
                item: item,
                targetRepoPath: targetRepoPath,
                properties: properties
        )
        hook('storage.afterCopy', json)
    }

    /**
     * Handle before property create events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the item on which the property is being set.
     * name (java.lang.String) - the name of the property being set.
     * values (java.lang.String[]) - A string array of values being assigned to the property.
     */
    beforePropertyCreate { item, name, values ->
        def json = new JsonBuilder()
        json (
                item: item,
                name: name,
                values: values
        )
        hook('storage.beforePropertyCreate', json)
    }
    /**
     * Handle after property create events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the item on which the property has been set.
     * name (java.lang.String) - the name of the property that has been set.
     * values (java.lang.String[]) - A string array of values assigned to the property.
     */
    afterPropertyCreate { item, name, values ->
        def json = new JsonBuilder()
        json (
                item: item,
                name: name,
                values: values
        )
        hook('storage.afterPropertyCreate', json)
    }
    /**
     * Handle before property delete events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the item from which the property is being deleted.
     * name (java.lang.String) - the name of the property being deleted.
     */
    beforePropertyDelete { item, name ->
        def json = new JsonBuilder()
        json (
                item: item,
                name: name
        )
        hook('storage.beforePropertyDelete', json)
    }
    /**
     * Handle after property delete events.
     *
     * Closure parameters:
     * item (org.artifactory.fs.ItemInfo) - the item from which the property has been deleted.
     * name (java.lang.String) - the name of the property that has been deleted.
     */
    afterPropertyDelete { item, name ->
        def json = new JsonBuilder()
        json (
                item: item,
                name: name
        )
        hook('storage.afterPropertyDelete', json)
    }
}

import org.artifactory.security.RealmPolicy
realms {

    /**
     * A security realm definition.
     * The first value is a unique name for the realm.
     *
     * Closure parameters:
     * autoCreateUsers (boolean) - Whether to automatically create users in Artifactory upon successful login. Defaults to
     * true. When false, the user will be transient and his privileges will be managed according to permissions defined for auto-join groups.
     * realmPolicy (org.artifactory.security.RealmPolicy): (Optional) - If included with value RealmPolicy.ADDITIVE, plugin will be executed only if the user has previously been authenticated, and allows enrichment of the authenticated
     * user with additional data.
     * See our public GitHub for an example here: https://github.com/JFrogDev/artifactory-user-plugins/blob/master/security/synchronizeLdapGroups/synchronizeLdapGroups.groovy
     */

    myRealm(autoCreateUsers: true, realmPolicy: RealmPolicy.ADDITIVE) {
        /**
         * Implementation should return true/false as the result of the authentication.
         *
         * Context variables:
         * groups (java.lang.String[]) - An array of groups that the authenticated user should be associated with (since 3.0.2).
         * user (org.artifactory.security.User) - The authenticated user.
         *
         * Closure parameters:
         * username (java.lang.String) - The username
         * credentials (java.lang.String) - The password
         */
        authenticate { username, credentials ->
            hook('realms.myRealm.authenticate', new JsonBuilder(username, credentials))
        }

        /**
         * Implementation should return true if the user is found in the realm.
         * Closure parameters:
         * username (java.lang.String) - The username
         */
        userExists { username ->
            hook('realms.myRealm.userExists', new JsonBuilder(username))
        }
    }
}

build {

    /**
     * Handle before build info save events
     *
     * Closure parameters:
     * buildRun (org.artifactory.build.DetailedBuildRun) - Build Info model to be saved. Partially mutable.
     */
    beforeSave { buildRun ->
        hook('build.beforeSave', new JsonBuilder(buildRun))
    }

    /**
     * Handle after build info save events
     *
     * Closure parameters:
     * buildRun (org.artifactory.build.DetailedBuildRun) - Build Info that was saved. Partially mutable.
     */
    afterSave { buildRun ->
        hook('build.afterSave', new JsonBuilder(buildRun))
    }
}

promotions {

    /**
     * A REST executable build promotion definition.
     *
     * Context variables:
     * status (int) - a response status code. Defaults to -1 (unset).
     * message (java.lang.String) - a text message to return in the response body, replacing the response content. Defaults to null.
     *
     * Plugin info annotation parameters:
     * version (java.lang.String) - Closure version. Optional.
     * description (java.lang.String - Closure description. Optional.
     * params (java.util.Map<java.lang.String, java.lang.String>) - Closure parameters. Optional.
     * users (java.util.Set<java.lang.String>) - Users permitted to query this plugin for information or invoke it.
     * groups (java.util.Set<java.lang.String>) - Groups permitted to query this plugin for information or invoke it.
     *
     * Closure parameters:
     * buildName (java.lang.String) - The build name specified in the REST request.
     * buildNumber (java.lang.String) - The build number specified in the REST request.
     * params (java.util.Map<java.lang.String, java.util.List<java.lang.String>>) - The parameters specified in the REST request.
     */
    promotionName { buildName, buildNumber, params ->
        def json = new JsonBuilder()
        json (
                buildName: buildName,
                buildNumber: buildNumber,
                params: params
        )
        hook('promotions.promotionName', json)
    }
}

replication {
    /**
     * Handle before file replication events.
     *
     * Context variables:
     * skip (boolean) - whether to skip replication for the current item. Defaults to false. Set to true to skip replication.
     * targetInfo (org.artifactory.addon.replication.ReplicationTargetInfo) - contains information about the replication target server
     *
     * Closure parameters:
     * localRepoPath (org.artifactory.repo.RepoPath) - the repoPath of the item on the local Artifactory server.
     */
    beforeFileReplication { localRepoPath ->
        hook('replication.beforeFileReplication', new JsonBuilder(localRepoPath))
    }
    /**
     * Handle before directory replication events.
     *
     * Context variables:
     * skip (boolean) - whether to skip replication for the current item. Defaults to false. Set to true to skip replication.
     * targetInfo (org.artifactory.addon.replication.ReplicationTargetInfo) - contains information about the replication target server
     *
     * Closure parameters:
     * localRepoPath (org.artifactory.repo.RepoPath) - the repoPath of the item on the local Artifactory server.
     */
    beforeDirectoryReplication { localRepoPath ->
        hook('replication.beforeDirectoryReplication', new JsonBuilder(localRepoPath))
    }
    /**
     * Handle before delete replication events.
     *
     * Context variables:
     * skip (boolean) - whether to skip replication for the current item. Defaults to false. Set to true to skip replication.
     * targetInfo (org.artifactory.addon.replication.ReplicationTargetInfo) - contains information about the replication target server
     *
     * Closure parameters:
     * localRepoPath (org.artifactory.repo.RepoPath) - the repoPath of the item on the local Artifactory server.
     */
    beforeDeleteReplication { localRepoPath ->
        hook('replication.beforeDeleteReplication', new JsonBuilder(localRepoPath))
    }
    /**
     * Handle before property replication events.
     *
     * Context variables:
     * skip (boolean) - whether to skip replication for the current item. Defaults to false. Set to true to skip replication.
     * targetInfo (org.artifactory.addon.replication.ReplicationTargetInfo) - contains information about the replication target server
     *
     * Closure parameters:
     * localRepoPath (org.artifactory.repo.RepoPath) - the repoPath of the item on the local Artifactory server.
     */
    beforePropertyReplication { localRepoPath ->
        hook('replication.beforePropertyReplication', new JsonBuilder(localRepoPath))
    }
    /**
     * Handle before statistics replication events.
     *
     * Context variables:
     * skip (boolean) - whether to skip replication for the current item. Defaults to false. Set to true to skip replication.
     * targetInfo (org.artifactory.addon.replication.ReplicationTargetInfo) - contains information about the replication target server
     *
     * Closure parameters:
     * localRepoPath (org.artifactory.repo.RepoPath) - the repoPath of the item on the local Artifactory server.
     */
    beforeStatisticsReplication { localRepoPath ->
        hook('replication.beforeStatisticsReplication', new JsonBuilder(localRepoPath))
    }
}

/**
 * hook method for script invocation
 * @param event
 * @param data
 * @return
 */
def hook(String event, JsonBuilder data) {
    if (WebHook.active(event)) {
        def builder = WebHook.eventBuilder(event, data)
        message = WebHook.run(event, builder)
    }
}

/**
 * WebHook class for handling grunt work
 * config load webhook.properties upon startup or REST API execute/webhookReload
 *
 */
class WebHook {

    private static WebHook me
    def triggers = new HashMap()

    static String run (String event, Object json) {
        init()
        return me.process(event, json)
    }

    static boolean active(String event) {
        init()

        if (me.triggers.get(event)) {
            return true
        } else {
            return false
        }
    }

    static JsonBuilder eventBuilder(String event, JsonBuilder data) {
        def builder = new JsonBuilder()
        builder.artifactory {
            webhook (
                    event:  event,
                    data:  data.content
            )
        }
        return builder
    }

    protected String process(String event, Object json) {
        if (active(event)) {
            def urlString = triggers.get(event)
            if (urlString) {
                def result
                try{
                    result = callPost(urlString, json.toString())
                } catch (Exception e) {
                    result = "Error: failure calling ${urlString}"
                }
                return result
            }
        }
    }

    protected boolean valid(String urlString) {
        def result
        try {
            JsonBuilder bdr = eventBuilder("ping", new JsonBuilder("test"))
            result = callPost(urlString, bdr.toString())
        } catch (Exception e) {
            result = "Error"
        }
        if (result && !(result =~ "Error")) {
            return true
        } else {
            return false
        }
    }

    protected String callPost(String urlString, String content) {

        def post = new URL(urlString).openConnection()
        post.method = "POST"
        post.doOutput = true
        post.setRequestProperty("Content-Type", "application/json")
        post.getOutputStream().write(content.getBytes("UTF-8"))
        def postRC = post.getResponseCode()

        return postRC.equals(200) ? post.inputStream.text : postRC
    }

    static void reload() {
        me = null
        init()
    }

    private static void init() {
        if (me == null) {
            me = new WebHook()
            me.loadProperties()
        }
    }

    private void loadProperties() {
        final String PROPERTIES_FILE_PATH = "${System.properties.'artifactory.home'}/etc/plugins/webhook.properties"

        def config = new ConfigSlurper().parse(new File(PROPERTIES_FILE_PATH).toURL())
        if (config) {
            def keys = config.webhook.keySet()
            if (keys) {
                for (key in keys) {
                    ConfigObject cfg = config.webhook.get(key)
                    if (cfg.url && valid(cfg.url)) {

                        if (cfg.event && ! me.triggers.get(cfg.event)) {
                            /**
                             * register event
                             */
                            if (cfg.event.contains(",")) {
                                def events = cfg.event.split(',')
                                for (def evt : events) {
                                    me.triggers.put(evt, cfg.url)
                                }
                            } else {
                                me.triggers.put(cfg.event, cfg.url)
                            }
                        }
                    }
                }
            }
        }
    }
}