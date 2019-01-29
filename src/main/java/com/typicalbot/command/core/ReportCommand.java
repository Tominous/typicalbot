/**
 * Copyright 2016-2019 Bryan Pikaard & Nicholas Sylke
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
package com.typicalbot.command.core;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.shard.Shard;
import com.typicalbot.shard.ShardManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

@CommandConfiguration(category = CommandCategory.CORE, aliases = "report")
public class ReportCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        // TODO(nsylke): Ratelimit to 24h per report
        if (!argument.has()) {
            context.sendMessage("You can't report nothing...");
            return;
        }

        // We are hardcoding these values as they should always be present. We may move this to a config option later down the line.
        // TypicalBot's Lounge
        Shard shard = ShardManager.getShard(163038706117115906L);
        Guild guild = shard.getInstance().getGuildById(163038706117115906L);

        // Report channel
        TextChannel channel = guild.getTextChannelById(533010867374129152L);

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Report");
        builder.setDescription(argument.toString());
        builder.setFooter(context.getMessage().getAuthor().getAsTag() + " (" + context.getMessage().getAuthor().getId() + ")", context.getMessage().getAuthor().getEffectiveAvatarUrl());

        channel.sendMessage(builder.build()).queue();

        // Reset builder
        builder.clear();

        builder.setTitle("Report");
        builder.setDescription("Successfully sent report to the TypicalBot Maintainers.");

        context.sendEmbed(builder.build());
    }
}
