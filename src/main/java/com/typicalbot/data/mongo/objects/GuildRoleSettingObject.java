/*
 * Copyright 2019 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.data.mongo.objects;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

@Embedded
public class GuildRoleSettingObject {
    @Property("admin")
    private long adminRole;

    @Property("mod")
    private long moderatorRole;

    @Property("dj")
    private long djRole;

    @Property("subscriber")
    private long subscriberRole;

    @Property("mute")
    private long muteRole;

    @Property("blacklist")
    private long blacklistRole;

    @Property("autorole")
    private long autorole;

    @Property("autoroleSilent")
    private boolean autoroleSilent;

    @Property("public")
    private long[] publicRoles;

    /*
     * messageId|emoteId|roleId
     */
    @Property("reaction")
    private String[] reactionRoles;

    public GuildRoleSettingObject() {
    }

    public long getAdminRole() {
        return this.adminRole;
    }

    public void setAdminRole(long adminRole) {
        this.adminRole = adminRole;
    }

    public long getModeratorRole() {
        return this.moderatorRole;
    }

    public void setModeratorRole(long moderatorRole) {
        this.moderatorRole = moderatorRole;
    }

    public long getDjRole() {
        return this.djRole;
    }

    public void setDjRole(long djRole) {
        this.djRole = djRole;
    }

    public long getSubscriberRole() {
        return this.subscriberRole;
    }

    public void setSubscriberRole(long subscriberRole) {
        this.subscriberRole = subscriberRole;
    }

    public long getMuteRole() {
        return this.muteRole;
    }

    public void setMuteRole(long muteRole) {
        this.muteRole = muteRole;
    }

    public long getBlacklistRole() {
        return this.blacklistRole;
    }

    public void setBlacklistRole(long blacklistRole) {
        this.blacklistRole = blacklistRole;
    }

    public long getAutorole() {
        return this.autorole;
    }

    public void setAutorole(long autorole) {
        this.autorole = autorole;
    }

    public boolean isAutoroleSilent() {
        return this.autoroleSilent;
    }

    public void setAutoroleSilent(boolean autoroleSilent) {
        this.autoroleSilent = autoroleSilent;
    }

    public long[] getPublicRoles() {
        return this.publicRoles;
    }

    public void setPublicRoles(long[] publicRoles) {
        this.publicRoles = publicRoles;
    }
}
