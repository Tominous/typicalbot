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
package com.typicalbot.nxt.command.moderation;

import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandCheck;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.nxt.util.SentryUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "ban")
public class BanCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "ban [@user]",
            "ban [@user] [reason]",
            "ban [@user] [purge]",
            "ban [@user] [purge] [reason]"
        };
    }

    @Override
    public String description() {
        return "Ban a member from the server.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getMember(), Permission.BAN_MEMBERS);
        CommandCheck.checkPermission(context.getSelfMember(), Permission.BAN_MEMBERS);
        CommandCheck.checkArguments(argument);

        User temp = context.getUser(argument.get(0));
        if (temp == null) {
            context.sendMessage("The user `{0}` does not exist.", argument.get(0));
            return;
        }

        if (!context.getMember().canInteract(context.getGuild().getMember(temp))) {
            context.sendMessage("You do not have permission to ban that user.");
            return;
        }

        if (!context.getSelfMember().canInteract(context.getGuild().getMember(temp))) {
            context.sendMessage("TypicalBot does not have permission to ban that user.");
            return;
        }

        int purge = 0;
        if (argument.getArguments().size() >= 2 && argument.get(1).matches("^[0-9]")) {
            try {
                purge = Integer.parseInt(argument.get(1));

                if (purge < 0) {
                    context.sendMessage("Cannot purge less than 0 day.");
                    return;
                }

                if (purge > 7) {
                    context.sendMessage("Cannot purge more than 7 days.");
                    return;
                }
            } catch (NumberFormatException ex) {
                SentryUtil.capture(ex, BanCommand.class);
            }
        }

        String reason;
        if (argument.getArguments().size() >= 2 && !argument.get(1).matches("^[0-9]")) {
            reason = String.join(" ", argument.getArguments().subList(1, argument.getArguments().size()));
        } else if (argument.getArguments().size() >= 3) {
            reason = String.join(" ", argument.getArguments().subList(2, argument.getArguments().size()));
        } else {
            reason = "No reason specified.";
        }

        context.getGuild().ban(temp.getId(), purge, reason).queue(o -> {
            context.sendMessage("Successfully banned {0} for {1}.", temp.getAsTag(), reason);

            // Mod log
        });
    }
}
