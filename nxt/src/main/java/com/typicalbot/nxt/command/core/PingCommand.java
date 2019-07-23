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
package com.typicalbot.nxt.command.core;

import com.typicalbot.command.*;
import com.typicalbot.nxt.shard.ShardManager;

@CommandConfiguration(category = CommandCategory.CORE, aliases = {"ping", "pong"})
public class PingCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "ping"
        };
    }

    @Override
    public String description() {
        return "A check to see if TypicalBot is able to respond.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        context.getJDA().getRestPing().queue(ping -> {
            context.sendMessage("Pong! Rest: {0}ms | Websocket: {1}ms | Average Shard: {2}ms",
                ping, context.getJDA().getGatewayPing(), (int) ShardManager.getAveragePing());
        });
    }
}
