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
package com.typicalbot.core.shard;

public class ShardManager {
    private static int MAX_SHARDS;

    private static Shard[] shards;

    public static void register(int shardTotal) throws InterruptedException {
        MAX_SHARDS = shardTotal;

        shards = new Shard[shardTotal];

        for (int i = 0; i < shardTotal; i++) {
            Shard shard = new Shard(i, shardTotal);
            shards[i] = shard;

            // Clients are limited to 1 identify every 5 seconds.
            Thread.sleep(5000);
        }
    }

    public static Shard[] getShards() {
        return shards;
    }
}
