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
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "kick")
public class KickCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "kick [@user]",
            "kick [@user] [reason]"
        };
    }

    @Override
    public String description() {
        return "Kick a member from the server.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getMember(), Permission.KICK_MEMBERS);
        CommandCheck.checkPermission(context.getSelfMember(), Permission.KICK_MEMBERS);
        CommandCheck.checkArguments(argument);

        User temp = context.getUser(argument.get(0));
        if (temp == null) {
            context.sendMessage("The user `{0}` does not exist.", argument.get(0));
            return;
        }

        if (!context.getMember().canInteract(context.getGuild().getMember(temp))) {
            context.sendMessage("You do not have permission to kick that user.");
            return;
        }

        if (!context.getSelfMember().canInteract(context.getGuild().getMember(temp))) {
            context.sendMessage("TypicalBot does not have permission to kick that user.");
            return;
        }

        String reason = String.join(" ", argument.getArguments().subList(1, argument.getArguments().size()));
        context.getGuild().kick(temp.getId(), reason).queue(o -> context.sendMessage("Successfully kicked {0} for {1}.", temp.getAsTag(), reason));
    }
}
