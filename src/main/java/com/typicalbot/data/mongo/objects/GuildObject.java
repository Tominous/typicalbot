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

import net.dv8tion.jda.core.entities.Guild;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value = "guilds", noClassnameStored = true)
public class GuildObject {
    @Id
    @Property("_id")
    private long id;

    @Property("name")
    private String name;

    @Property("iconId")
    private String iconId;

    @Property("ownerId")
    private long ownerId;

    @Embedded
    private GuildSettingObject guildSettings;

    public GuildObject() {
    }

    public GuildObject(Guild guild) {
        this.id = guild.getIdLong();
        this.name = guild.getName();
        this.iconId = guild.getIconId();
        this.ownerId = guild.getOwnerIdLong();
        this.guildSettings = new GuildSettingObject();
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconId() {
        return this.iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public GuildSettingObject getGuildSettings() {
        return this.guildSettings;
    }

    public void setGuildSettings(GuildSettingObject guildSettings) {
        this.guildSettings = guildSettings;
    }
}
