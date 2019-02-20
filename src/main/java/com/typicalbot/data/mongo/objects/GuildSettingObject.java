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
public class GuildSettingObject {
    @Property("prefix")
    private String prefix;

    @Property("locale")
    private String locale;

    @Property("mode")
    private int mode;

    @Property("announcements")
    private long announcementsId;

    @Property("welcomeMessage")
    private String welcomeMessage;

    @Property("goodbyeMessage")
    private String goodbyeMessage;

    @Property("ignoredChannels")
    private long[] ignoredChannels;

    @Property("nickname")
    private boolean nickname;

    @Embedded
    private GuildFilterSettingObject filters;

    @Embedded
    private GuildLogSettingObject logs;

    @Embedded
    private GuildModuleSettingObject modules;

    @Embedded
    private GuildMusicSettingObject music;

    @Embedded
    private GuildRoleSettingObject roles;

    public GuildSettingObject() {
        this.filters = new GuildFilterSettingObject();
        this.logs = new GuildLogSettingObject();
        this.modules = new GuildModuleSettingObject();
        this.music = new GuildMusicSettingObject();
        this.roles = new GuildRoleSettingObject();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public long getAnnouncementsId() {
        return this.announcementsId;
    }

    public void setAnnouncementsId(long announcementsId) {
        this.announcementsId = announcementsId;
    }

    public String getWelcomeMessage() {
        return this.welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getGoodbyeMessage() {
        return this.goodbyeMessage;
    }

    public void setGoodbyeMessage(String goodbyeMessage) {
        this.goodbyeMessage = goodbyeMessage;
    }

    public long[] getIgnoredChannels() {
        return this.ignoredChannels;
    }

    public void setIgnoredChannels(long[] ignoredChannels) {
        this.ignoredChannels = ignoredChannels;
    }

    public boolean isNickname() {
        return this.nickname;
    }

    public void setNickname(boolean nickname) {
        this.nickname = nickname;
    }

    public GuildFilterSettingObject getFilters() {
        return this.filters;
    }

    public void setFilters(GuildFilterSettingObject filters) {
        this.filters = filters;
    }

    public GuildLogSettingObject getLogs() {
        return this.logs;
    }

    public void setLogs(GuildLogSettingObject logs) {
        this.logs = logs;
    }

    public GuildModuleSettingObject getModules() {
        return this.modules;
    }

    public void setModules(GuildModuleSettingObject modules) {
        this.modules = modules;
    }

    public GuildMusicSettingObject getMusic() {
        return this.music;
    }

    public void setMusic(GuildMusicSettingObject music) {
        this.music = music;
    }

    public GuildRoleSettingObject getRoles() {
        return this.roles;
    }

    public void setRoles(GuildRoleSettingObject roles) {
        this.roles = roles;
    }
}
