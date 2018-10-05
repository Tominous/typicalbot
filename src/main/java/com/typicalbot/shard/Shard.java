/**
 * TypicalBot - A multipurpose discord bot
 * Copyright (C) 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.typicalbot.shard;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class Shard {
    private final int shardId;
    private final int shardCount;

    private JDA instance;

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
                .build();
    }

    public JDA getInstance() {
        return this.instance;
    }
}
