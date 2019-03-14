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
package com.typicalbot.shard;

import com.google.common.primitives.Ints;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class ShardManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShardManager.class);

    /**
     * The maximum amount of shards.
     */
    private static int maxShards;

    /**
     * Allows for TypicalBot to track which shards are operational and
     * non-operational, while also allowing the system to access any and
     * all server settings and commands.
     */
    private static Shard[] shards;

    // Prevent instantiation.
    private ShardManager() {
    }

    /**
     * Generate shards to run the Discord bot. Bots are limited to 2500
     * servers per shard.
     *
     * @param token      the token of the Discord bot.
     * @param clientId   the client identifier of the Discord bot.
     * @param shardTotal the maximum shards to generate.
     * @throws InterruptedException if the thread is interrupted.
     */
    public static void register(String token, String clientId, int shardTotal) throws InterruptedException {
        maxShards = shardTotal;
        shards = new Shard[shardTotal];

        for (int i = 0; i < shardTotal; i++) {
            Shard shard = new Shard(token, clientId, i, shardTotal);
            shards[i] = shard;

            LOGGER.debug("Shard {} is starting...", i);

            // Clients are limited to one identify every 5 seconds.
            Thread.sleep(5000);
        }
    }

    /**
     * Get a list of all shards.
     *
     * @return list of shards.
     */
    public static Shard[] getShards() {
        return shards;
    }

    /**
     * Get a shard from using the id of the shard
     *
     * @param shardId the id of the shard
     * @return the shard
     */
    public static Shard getShard(int shardId) {
        return shards[shardId];
    }

    /**
     * Get a shard from using the guild id.
     *
     * @param guildId the id of the guild
     * @return the shard
     */
    public static Shard getShard(long guildId) {
        // Sharding Formula from Discord developer documentation.
        long shardId = (guildId >> 22) % maxShards;

        return getShard(Ints.checkedCast(shardId));
    }

    /**
     * Restart a specify shard.
     *
     * @param token    The token of the bot
     * @param clientId The client identifier of the bot
     * @param shardId  The shard identifier
     * @throws InterruptedException if the thread is interrupted
     */
    public static void restart(String token, String clientId, int shardId) throws InterruptedException {
        Shard shard = getShard(shardId);

        // Stop the shard
        shard.shutdown();
        Thread.sleep(5000);

        shard = new Shard(token, clientId, shardId, maxShards);
        shards[shardId] = shard;
    }

    /**
     * Acquires the recommended shard count by sending a REST request to the Discord API.
     *
     * @param token the bot token
     * @return recommended shard count
     */
    public static int getRecommendedShards(String token) {
        int count = 0;

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                .url("https://discordapp.com/api/gateway/bot")
                .addHeader("Authorization", "Bot " + token)
                .addHeader("Content-Type", "application/json")
                .build();

            Response response = client.newCall(request).execute();

            count = new JSONObject(response.body().string()).getInt("shards");

            response.close();
        } catch (IOException ex) {
            LOGGER.error("Unable to retrieve recommended shard count.", ex);
        }

        return count;
    }

    /**
     * Get the ping from all shards and average it out.
     *
     * @return average ping
     */
    public static double getAveragePing() {
        return Arrays.stream(shards).mapToLong(Shard::getPing).filter(ping -> ping != -1).average().orElse(-1D);
    }

    public static int getGuildCount() {
        return Arrays.stream(shards).mapToInt(Shard::getGuilds).sum();
    }

    public static int getChannelCount() {
        return Arrays.stream(shards).mapToInt(Shard::getChannels).sum();
    }

    public static int getUserCount() {
        return Arrays.stream(shards).mapToInt(Shard::getUsers).sum();
    }

    public static int getVoiceConnectionCount() {
        return Arrays.stream(shards).mapToInt(Shard::getVoiceConnections).sum();
    }
}
