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
import net.dv8tion.jda.core.entities.User;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "kick")
public class KickCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!context.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            context.sendMessage("You do not have permission to kick members.");
            return;
        }

        if (!context.getSelfMember().hasPermission(Permission.KICK_MEMBERS)) {
            context.sendMessage("TypicalBot does not have permission to kick members.");
            return;
        }

        if (!argument.has()) {
            context.sendMessage("Incorrect usage. Please check `$help kick` for usage.");
            return;
        }

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
        context.getGuild().getController().kick(temp.getId(), reason).queue(o -> context.sendMessage("Successfully kicked {0} for {1}.", temp.getAsTag(), reason));
    }
}
