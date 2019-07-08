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
package com.typicalbot.nxt.data.mongo.objects;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

@Embedded
public class GuildModuleSettingObject {
    @Property("fun")
    private boolean fun;

    @Property("integration")
    private boolean integration;

    @Property("interaction")
    private boolean interaction;

    @Property("miscellaneous")
    private boolean miscellaneous;

    @Property("moderation")
    private boolean moderation;

    @Property("music")
    private boolean music;

    @Property("utility")
    private boolean utility;

    public GuildModuleSettingObject() {
    }

    public boolean isFun() {
        return this.fun;
    }

    public void setFun(boolean fun) {
        this.fun = fun;
    }

    public boolean isIntegration() {
        return this.integration;
    }

    public void setIntegration(boolean integration) {
        this.integration = integration;
    }

    public boolean isInteraction() {
        return this.interaction;
    }

    public void setInteraction(boolean interaction) {
        this.interaction = interaction;
    }

    public boolean isMiscellaneous() {
        return this.miscellaneous;
    }

    public void setMiscellaneous(boolean miscellaneous) {
        this.miscellaneous = miscellaneous;
    }

    public boolean isModeration() {
        return this.moderation;
    }

    public void setModeration(boolean moderation) {
        this.moderation = moderation;
    }

    public boolean isMusic() {
        return this.music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public boolean isUtility() {
        return this.utility;
    }

    public void setUtility(boolean utility) {
        this.utility = utility;
    }
}
