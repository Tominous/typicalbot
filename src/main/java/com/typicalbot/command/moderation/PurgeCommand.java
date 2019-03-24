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
package com.typicalbot.command.moderation;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;

import java.util.concurrent.TimeUnit;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "purge")
public class PurgeCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "purge [count]",
            "purge all"
        };
    }

    @Override
    public String description() {
        return "Purge messages in a channel.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!context.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            context.sendMessage("You do not have permission to manage messages.");
            return;
        }

        if (!context.getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            context.sendMessage("TypicalBot does not have permission to manage messages.");
            return;
        }

        if (!argument.has()) {
            context.sendMessage("Incorrect usage. Please check `$help purge` for usage.");
            return;
        }

        if (argument.get(0).equalsIgnoreCase("all")) {
            context.getGuild().getController().createCopyOfChannel(context.getMessage().getTextChannel()).queue(
                channel -> context.getMessage().getTextChannel().delete().queueAfter(3, TimeUnit.SECONDS,
                    o -> ((MessageChannel) channel).sendMessage("Successfully purged all messages.").queue()
                )
            );
        } else {
            int amount = Integer.parseInt(argument.get(0));

            MessageHistory history = context.getChannel().getHistory();

            if (!(amount >= 2 && amount <= 100)) {
                context.sendMessage("You must choose a number between 2 and 100.");
                return;
            }

            history.retrievePast(amount).queue(
                messages -> context.getMessage().getTextChannel().deleteMessages(messages).queue(
                    o -> context.sendMessage("Successfully purged {0} messages.", amount)
                )
            );
        }
    }
}
