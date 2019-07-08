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
public class GuildFilterSettingObject {
    @Property("caps")
    private boolean caps;

    @Property("copypasta")
    private boolean copypasta;

    @Property("domain")
    private boolean domain;

    @Property("invite")
    private boolean invite;

    @Property("spam")
    private boolean spam;

    public GuildFilterSettingObject() {
    }

    public boolean isCaps() {
        return this.caps;
    }

    public void setCaps(boolean caps) {
        this.caps = caps;
    }

    public boolean isCopypasta() {
        return this.copypasta;
    }

    public void setCopypasta(boolean copypasta) {
        this.copypasta = copypasta;
    }

    public boolean isDomain() {
        return this.domain;
    }

    public void setDomain(boolean domain) {
        this.domain = domain;
    }

    public boolean isInvite() {
        return this.invite;
    }

    public void setInvite(boolean invite) {
        this.invite = invite;
    }

    public boolean isSpam() {
        return this.spam;
    }

    public void setSpam(boolean spam) {
        this.spam = spam;
    }
}
