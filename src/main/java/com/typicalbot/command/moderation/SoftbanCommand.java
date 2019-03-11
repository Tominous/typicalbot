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

import java.util.concurrent.TimeUnit;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "softban")
public class SoftbanCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!context.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            context.sendMessage("You do not have permission to ban members.");
            return;
        }

        if (!context.getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
            context.sendMessage("TypicalBot does not have permission to ban members.");
            return;
        }

        if (!argument.has()) {
            context.sendMessage("Incorrect usage. Please check `$help softban` for usage.");
            return;
        }

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

        String reason = String.join(" ", argument.getArguments().subList(1, argument.getArguments().size()));
        context.getGuild().getController().ban(temp.getId(), 1, reason).queue(o -> {
            context.sendMessage("Successfully softbanned {0} for {1}.", temp.getAsTag(), reason);
            context.getGuild().getController().unban(temp.getId()).reason(reason).queueAfter(3, TimeUnit.SECONDS);
        });
    }
}
