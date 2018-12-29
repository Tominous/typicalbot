/**
 * Copyright 2016-2018 Bryan Pikaard & Nicholas Sylke
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
package com.typicalbot.shard;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

// TODO(nsylke): Documentation
public class Shard {
    private final String clientId;
    private final int shardId;
    private final int shardTotal;

    private JDA instance;

    public Shard(String token) {
        this(token, null, 0, 1);
    }

    public Shard(String token, String clientId, int shardId, int shardTotal) {
        this.clientId = clientId;
        this.shardId = shardId;
        this.shardTotal = shardTotal;

        try {
            this.instance = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .setAutoReconnect(true)
                    // .setAudioEnabled(true)
                    .setGame(Game.playing("Client Started")) // Same as TypicalBot 2.x
                    .setStatus(OnlineStatus.IDLE) // Set to IDLE while still loading, change ONLINE when ready
                    .setBulkDeleteSplittingEnabled(true)
                    .setEnableShutdownHook(true)
                    // .setAudioSendFactory(new NativeAudioSendFactory())
                    .useSharding(shardId, shardTotal)
                    .build();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    public String getClientId() {
        if (this.clientId == null) {
            return Long.toString(this.instance.getSelfUser().getIdLong());
        }

        return this.clientId;
    }

    public int getShardId() {
        return this.shardId;
    }

    public int getShardTotal() {
        return this.shardTotal;
    }

    public JDA getInstance() {
        return this.instance;
    }

    public void shutdown() {
        this.instance.shutdown();
    }
}
