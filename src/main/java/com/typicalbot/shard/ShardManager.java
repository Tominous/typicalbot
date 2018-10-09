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

import com.google.common.primitives.Ints;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public class ShardManager {
    /**
     * The maximum shard instances available in current session.
     */
    private static int MAX_SHARDS;

    /**
     * A collection of shard instances.
     */
    private static Shard[] shards;

    // Suppresses default constructor, ensuring non-instantiability.
    private ShardManager() {
    }

    /**
     * Register {@link Shard} instances.
     *
     * @param shardCount The amount of shards to be registered.
     * @throws LoginException
     * @throws InterruptedException
     */
    public static void register(int shardCount) throws LoginException, InterruptedException {
        // This should eventually be changed to an option in the configuration
        // and allow for on-demand shard addition/deletion.
        MAX_SHARDS = shardCount;

        shards = new Shard[MAX_SHARDS];

        for (int i = 0; i < shardCount; i++) {
            Shard shard = new Shard(i, shardCount);
            shards[i] = shard;

            // Clients are limited to 1 identity every 5 seconds.
            Thread.sleep(5000);
        }
    }

    /**
     * Get the shard instance from a guild ID.
     *
     * @param guildId The guild ID.
     * @return The {@link Shard} instance the guild is active on.
     */
    public static Shard getShard(long guildId) {
        // The sharding formula from Discord developer documentation.
        long shardId = (guildId >> 22) % MAX_SHARDS;

        return getShard(Ints.checkedCast(shardId));
    }

    /**
     * Get the shard instance from an ID.
     *
     * @param shardId The shard ID.
     * @return The {@link Shard} instance.
     */
    public static Shard getShard(int shardId) {
        return shards[shardId];
    }

    /**
     * Restart a shard instance.
     *
     * @param shardId The shard ID.
     * @throws LoginException
     */
    public static void restart(int shardId) throws LoginException {
        Shard shard = getShard(shardId);

        // Shut down the shard through JDA.
        shard.getInstance().shutdown();

        // Create a new instance of the specific shard.
        shard = new Shard(shardId, MAX_SHARDS);

        // Update the shard instance in the shard collection.
        shards[shardId] = shard;
    }

    /**
     * Get the average ping of all shard instances.
     *
     * @return The average ping.
     */
    public static int getAveragePing() {
        return (int) Arrays.stream(shards).mapToLong(Shard::getPing).filter(ping -> ping != -1).average().orElse(-1D);
    }

    /**
     * Get all shards.
     *
     * @return The shard collection.
     */
    public static Shard[] getShards() {
        return shards;
    }
}
