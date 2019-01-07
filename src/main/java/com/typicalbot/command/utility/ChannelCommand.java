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
package com.typicalbot.command.utility;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.util.StringUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"channel", "channelinfo", "cinfo"})
public class ChannelCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            context.sendMessage("Incorrect usage.");
            return;
        }

        TextChannel channel = context.getMessage().getMentionedChannels().get(0);

        if (channel == null) {
            context.sendMessage("The channel specified does not exist.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("#" + channel.getName());
        builder.setDescription(channel.getTopic());
        builder.addField("ID", Long.toString(channel.getIdLong()), true);
        builder.addField("Position", Integer.toString(channel.getPosition()), true);
        builder.addField("Slowmode", String.format("%d seconds", channel.getSlowmode()), true);
        builder.addField("NSFW", StringUtil.firstUpperCase(Boolean.toString(channel.isNSFW())), true);
        if (channel.isNSFW()) builder.setColor(0xff0000);
        builder.setTimestamp(channel.getCreationTime());

        context.sendEmbed(builder.build());
    }
}
