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
package com.typicalbot.nxt.command.utility;

import com.typicalbot.command.*;
import com.typicalbot.nxt.util.StringUtil;
import com.typicalbot.util.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

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
        builder.setColor(Color.TYPICALBOT_BLUE.rgb());

        if (guild.getDescription() != null) {
            builder.setDescription(guild.getDescription());
        }

        builder.addField("Name", guild.getName(), true);
        builder.addField("ID", guild.getId(), true);
        builder.addField("Owner", guild.getOwner().getUser().getAsTag(), true);
        builder.addField("Owner ID", guild.getOwnerId(), true);
        builder.addField("Region", guild.getRegion().getName(), true);
        builder.addField("Verification", StringUtil.capitalize(guild.getVerificationLevel().name()), true);
        builder.addField("Explicit Level", StringUtil.capitalize(guild.getExplicitContentLevel().name().replace('_', ' ')), true);
        builder.addField("Members", Integer.toString(guild.getMembers().size()), true);
        builder.addField("Channels", Integer.toString(guild.getChannels().size()), true);
        builder.addField("Roles", Integer.toString(guild.getRoles().size()), true);
        builder.addField("Emotes", Integer.toString(guild.getEmotes().size()), true);
        builder.addField("Boosted Tier", StringUtil.capitalize(guild.getBoostTier().name().replace('_', ' ')), true);
        builder.addField("Boosters", guild.getBoosters().stream().map(Member::getEffectiveName).collect(Collectors.joining(", ")), false);

        String feature = "";
        if (!guild.getFeatures().isEmpty()) {
            feature = guild.getFeatures().toString();
            if (guild.getFeatures().contains("VERIFIED")) {
                feature = "Verified Server";
            } else if (guild.getFeatures().contains("PARTNERED")) {
                feature = "Discord Partner";
            }
        }

        builder.setFooter((feature.length() > 0 ? feature + " | " : "") + "Created on " + guild.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), null);

        context.sendEmbed(builder.build());
    }
}
