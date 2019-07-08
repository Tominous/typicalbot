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

import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandCheck;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.nxt.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"channel", "channelinfo", "cinfo"})
public class ChannelCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        GuildChannel channel;

        if (!argument.has()) {
            channel = context.getMessage().getTextChannel();
        } else {
            channel = context.getChannel(argument.get(0));
        }

        if (channel == null) {
            context.sendMessage("The channel specified does not exist.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(channel.getName());
        builder.addField("ID", Long.toString(channel.getIdLong()), true);
        builder.addField("Position", Integer.toString(channel.getPosition()), true);
        builder.addField("Type", StringUtil.capitalize(channel.getType().toString()), true);

        if (channel instanceof TextChannel) {
            builder.setDescription(((TextChannel) channel).getTopic());
            builder.addField("Slowmode", String.format("%d seconds", ((TextChannel) channel).getSlowmode()), true);
            builder.addField("NSFW", StringUtil.capitalize(Boolean.toString(((TextChannel) channel).isNSFW())), true);
            builder.addBlankField(true);
            if (((TextChannel) channel).isNSFW()) builder.setColor(0xff0000);
        }

        if (channel instanceof VoiceChannel) {
            builder.addField("Bitrate", String.format("%dkbps", ((VoiceChannel) channel).getBitrate()), true);

            String userLimit = ((VoiceChannel) channel).getUserLimit() == 0 ? "None" : Integer.toString(((VoiceChannel) channel).getUserLimit());

            builder.addField("User Limit", userLimit, true);
            builder.addBlankField(true);
        }

        builder.setTimestamp(channel.getTimeCreated());

        context.sendEmbed(builder.build());
    }
}
