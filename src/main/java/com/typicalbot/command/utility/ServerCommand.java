/**
 * Copyright 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.command.utility;

import com.typicalbot.command.CommandPermission;
import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"server", "serverinfo", "sinfo"})
public class ServerCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        EmbedBuilder builder = new EmbedBuilder();

        Guild guild = context.getMessage().getGuild();

        builder.setTitle("Server Information");
        builder.setThumbnail(guild.getIconUrl());

        builder.addField("Name", guild.getName(), true);
        builder.addField("ID", guild.getId(), true);
        builder.addField("Owner", guild.getOwner().getUser().getAsTag(), true);
        builder.addField("Region", guild.getRegion().getName(), true);
        builder.addField("Members", Integer.toString(guild.getMembers().size()), true);
        builder.addField("Channel", Integer.toString(guild.getChannels().size()), true);
        builder.addField("Verification", StringUtil.firstUpperCase(guild.getVerificationLevel().name()), true);
        builder.addField("Roles", Integer.toString(guild.getRoles().size()), true);
        builder.addField("Emojis", Integer.toString(guild.getEmotes().size()), true);

        context.sendEmbed(builder.build());
    }
}
