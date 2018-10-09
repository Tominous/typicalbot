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

import com.typicalbot.events.MessageEvent;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class Shard {
    /**
     * The current shard ID.
     */
    private final int shardId;

    /**
     * The total amount of shards.
     */
    private final int shardCount;

    /**
     * The JDA instance.
     */
    private JDA instance;

    /**
     * Default constructor.
     *
     * @param shardId The shard ID of this instance.
     * @param shardCount The total amount of shards.
     * @throws LoginException
     */
    public Shard(int shardId, int shardCount) throws LoginException {
        this.shardId = shardId;
        this.shardCount = shardCount;

        this.instance = new JDABuilder(AccountType.BOT)
                // Move token to configuration file
                .setToken("")
                .setAutoReconnect(true)
                .setGame(Game.of(Game.GameType.DEFAULT, "Client Started"))
                .setStatus(OnlineStatus.ONLINE)
                .setBulkDeleteSplittingEnabled(true)
                .setEnableShutdownHook(true)
                .useSharding(shardId, shardCount)
                .setCorePoolSize(8)
                .addEventListener(new MessageEvent())
                .build();
    }

    /**
     * Get the JDA instance.
     *
     * @return The JDA instance.
     */
    public JDA getInstance() {
        return this.instance;
    }

    /**
     * Get the ping of the current shard.
     *
     * @return The ping of the shard.
     */
    public long getPing() {
        return this.instance.getPing();
    }

    /**
     * Get the current shard ID.
     *
     * @return The shard ID.
     */
    public int getShardId() {
        return this.shardId;
    }

    /**
     * Get the total amount of shards.
     *
     * @return The total amount of shards.
     */
    public int getShardCount() {
        return this.shardCount;
    }
}
