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
package com.typicalbot.command.utility;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.shard.Shard;
import com.typicalbot.util.Pageable;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;

import java.text.DecimalFormat;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = "servers")
public class ServersCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        Pageable<Guild> guilds = new Pageable<>(Shard.getSingleton().getInstance().getGuilds());

        if (argument.has()) {
            guilds.setPage(Integer.parseInt(argument.get(0)));
        } else {
            guilds.setPage(1);
        }

        EmbedBuilder builder = new EmbedBuilder();
        DecimalFormat format = new DecimalFormat("#,###");

        builder.setTitle("Server List - Provided by TypicalBot");
        builder.setColor(CommandContext.TYPICALBOT_BLUE);
        builder.setThumbnail("https://cdn.discordapp.com/icons/509030978484699136/ce37733019bb77ecaed550dfdaacea89.png");

        for (Guild guild : guilds.getListForPage()) {
            builder.addField(guild.getName(), format.format(guild.getMembers().size()) + " Users", true);
        }

        builder.setFooter("Page " + guilds.getPage() + " / " + guilds.getMaxPages(), null);

        context.sendEmbed(builder.build());
    }
}
