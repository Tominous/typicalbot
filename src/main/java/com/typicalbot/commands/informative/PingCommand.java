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
package com.typicalbot.commands.informative;

import com.typicalbot.common.command.BaseCommand;
import com.typicalbot.common.command.annotation.Command;
import com.typicalbot.shard.ShardManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@Command(triggers = "ping|pong")
public class PingCommand implements BaseCommand {
    @Override
    public void invoke(String[] parts, Member author, TextChannel channel, Guild guild) {
        channel.sendMessage("Shard: " + ShardManager.getShard(guild.getIdLong()).getPing() + "ms | All Shards: " + ShardManager.getAveragePing() + "ms.").queue();
    }
}
