/*
 * Copyright 2019 Bryan Pikaard, Nicholas Sylke and the TypicalBot contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
public class GuildLogSettingObject {
    @Property("logsId")
    private long logsId;

    @Property("logJoin")
    private boolean logJoin;

    @Property("logLeave")
    private boolean logLeave;

    @Property("logVoiceJoin")
    private boolean logVoiceJoin;

    @Property("logVoiceLeave")
    private boolean logVoiceLeave;

    @Property("logNickname")
    private boolean logNickname;

    @Property("modlogsId")
    private long modlogsId;

    @Property("modlogBan")
    private boolean modlogBan;

    @Property("modlogUnban")
    private boolean modlogUnban;

    @Property("modlogKick")
    private boolean modlogKick;

    @Property("modlogMute")
    private boolean modlogMute;

    @Property("modlogUnmute")
    private boolean modlogUnmute;

    @Property("modlogPurge")
    private boolean modlogPurge;

    public GuildLogSettingObject() {
    }

    public long getLogsId() {
        return this.logsId;
    }

    public void setLogsId(long logsId) {
        this.logsId = logsId;
    }

    public boolean isLogJoin() {
        return this.logJoin;
    }

    public void setLogJoin(boolean logJoin) {
        this.logJoin = logJoin;
    }

    public boolean isLogLeave() {
        return this.logLeave;
    }

    public void setLogLeave(boolean logLeave) {
        this.logLeave = logLeave;
    }

    public boolean isLogVoiceJoin() {
        return this.logVoiceJoin;
    }

    public void setLogVoiceJoin(boolean logVoiceJoin) {
        this.logVoiceJoin = logVoiceJoin;
    }

    public boolean isLogVoiceLeave() {
        return this.logVoiceLeave;
    }

    public void setLogVoiceLeave(boolean logVoiceLeave) {
        this.logVoiceLeave = logVoiceLeave;
    }

    public boolean isLogNickname() {
        return this.logNickname;
    }

    public void setLogNickname(boolean logNickname) {
        this.logNickname = logNickname;
    }

    public long getModlogsId() {
        return this.modlogsId;
    }

    public void setModlogsId(long modlogsId) {
        this.modlogsId = modlogsId;
    }

    public boolean isModlogBan() {
        return this.modlogBan;
    }

    public void setModlogBan(boolean modlogBan) {
        this.modlogBan = modlogBan;
    }

    public boolean isModlogUnban() {
        return this.modlogUnban;
    }

    public void setModlogUnban(boolean modlogUnban) {
        this.modlogUnban = modlogUnban;
    }

    public boolean isModlogKick() {
        return this.modlogKick;
    }

    public void setModlogKick(boolean modlogKick) {
        this.modlogKick = modlogKick;
    }

    public boolean isModlogMute() {
        return this.modlogMute;
    }

    public void setModlogMute(boolean modlogMute) {
        this.modlogMute = modlogMute;
    }

    public boolean isModlogUnmute() {
        return this.modlogUnmute;
    }

    public void setModlogUnmute(boolean modlogUnmute) {
        this.modlogUnmute = modlogUnmute;
    }

    public boolean isModlogPurge() {
        return this.modlogPurge;
    }

    public void setModlogPurge(boolean modlogPurge) {
        this.modlogPurge = modlogPurge;
    }
}
