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

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Shard {
    private final int shardId;
    private final int shardTotal;

    private JDA instance;

    public Shard(int shardId, int shardTotal) {
        this.shardId = shardId;
        this.shardTotal = shardTotal;

        try {
            this.instance = new JDABuilder(AccountType.BOT)
                    .setToken("")
                    .setAutoReconnect(true)
                    .setBulkDeleteSplittingEnabled(true)
                    .setEnableShutdownHook(true)
                    .useSharding(shardId, shardTotal)
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
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

    public void stop() {
        this.instance.shutdown();
    }
}
