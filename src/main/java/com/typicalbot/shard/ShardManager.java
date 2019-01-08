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

import java.util.Arrays;

public class ShardManager {
    private static int MAX_SHARDS;

    /**
     * Allows for TypicalBot to track which shards are operational and
     * non-operational, while also allowing the system to access any and
     * all server settings and commands.
     */
    static Shard[] shards;

    /**
     * Generate shards to run the Discord bot. Bots are limited to 2500
     * servers per shard.
     *
     * @param token the token of the Discord bot.
     * @param clientId the client identifier of the Discord bot.
     * @param shardTotal the maximum shards to generate.
     * @throws InterruptedException if the thread is interrupted.
     */
    public static void register(String token, String clientId, int shardTotal) throws InterruptedException {
        MAX_SHARDS = shardTotal;

        shards = new Shard[shardTotal];

        for (int i = 0; i < shardTotal; i++) {
            Shard shard = new Shard(token, clientId, i, shardTotal);
            shards[i] = shard;

            // Clients are limited to one identify every 5 seconds.
            Thread.sleep(5000);
        }
    }

    public static Shard[] getShards() {
        return shards;
    }

    public static double getAveragePing() {
        return Arrays.stream(shards).mapToLong(Shard::getPing).filter(ping -> ping != -1).average().orElse(-1D);
    }
}
