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
import com.typicalbot.util.StringUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;

import java.time.format.DateTimeFormatter;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"server", "serverinfo", "sinfo"})
public class ServerCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        EmbedBuilder builder = new EmbedBuilder();

        Guild guild = context.getGuild();

        builder.setTitle("Server Information");
        builder.setThumbnail(guild.getIconUrl());
        builder.setColor(CommandContext.TYPICALBOT_BLUE);

        builder.addField("Name", guild.getName(), true);
        builder.addField("ID", guild.getId(), true);
        builder.addField("Owner", guild.getOwner().getUser().getAsTag(), true);
        builder.addField("Owner ID", guild.getOwnerId(), true);
        builder.addField("Region", guild.getRegion().getName(), true);
        builder.addField("Verification", StringUtil.capitalize(guild.getVerificationLevel().name()), true);

        if (context.getSelfMember().hasPermission(Permission.MESSAGE_EXT_EMOJI)) {
            String online = "<:online:552140814579400705>";
            String idle = "<:idle:552140814537588766>";
            String dnd = "<:dnd:552140814583857191>";
            String invisible = "<:invisible:552140814541783043>";

            long onlineCount = guild.getMemberCache().stream().filter(m -> m.getOnlineStatus() == OnlineStatus.ONLINE).count();
            long idleCount = guild.getMemberCache().stream().filter(m -> m.getOnlineStatus() == OnlineStatus.IDLE).count();
            long dndCount = guild.getMemberCache().stream().filter(m -> m.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB).count();
            long invisibleCount = guild.getMemberCache().stream().filter(m -> m.getOnlineStatus() == OnlineStatus.OFFLINE).count();

            builder.addField("Members", String.format("%s %d%n%s %d%n%s %d%n%s %d", online, onlineCount, idle, idleCount, dnd, dndCount, invisible, invisibleCount), true);
        } else {
            builder.addField("Members", Integer.toString(guild.getMembers().size()), true);
        }

        builder.addField("Channels", Integer.toString(guild.getChannels().size()), true);
        builder.addField("Roles", Integer.toString(guild.getRoles().size()), true);
        builder.addField("Emotes", Integer.toString(guild.getEmotes().size()), true);

        String feature = "";
        if (!guild.getFeatures().isEmpty()) {
            feature = (guild.getFeatures().contains("VERIFIED") ? "Verified Server" : "Discord Partner");
        }

        builder.setFooter((feature.length() > 0 ? feature + " | " : "") + "Created on " + guild.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), null);

        context.sendEmbed(builder.build());
    }
}
