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
