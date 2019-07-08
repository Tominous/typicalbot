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

package com.typicalbot.nxt.data.mongo.dao;

import com.typicalbot.nxt.Launcher;
import com.typicalbot.nxt.data.mongo.objects.GuildObject;
import net.dv8tion.jda.api.entities.Guild;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.Optional;

public class GuildDAO {
    private Datastore datastore = Launcher.getMongoManager().getDatastore();

    public Optional<GuildObject> get(long guildId) {
        Query<GuildObject> guild = this.datastore.createQuery(GuildObject.class).filter("_id", guildId);

        if (guild.asList().size() == 1) {
            return Optional.of(guild.get());
        }

        return Optional.empty();
    }

    public GuildObject create(Guild guild) {
        return new GuildObject(guild);
    }

    public void update(GuildObject object) {
        this.datastore.save(object);
    }

    public void delete(GuildObject object) {
        this.datastore.delete(object);
    }
}
