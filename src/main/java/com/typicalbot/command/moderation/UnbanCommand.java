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

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "unban")
public class UnbanCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "unban [@user]"
        };
    }

    @Override
    public String description() {
        return "Unban a member from the server.";
    }

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
            context.sendMessage("Incorrect usage. Please check `$help unban` for usage.");
            return;
        }

        context.getGuild().getBanById(argument.get(0)).queue(ban -> context.getGuild().getController().unban(ban.getUser()).queue(o -> context.sendMessage("Successfully unbanned {0}.", ban.getUser().getAsTag())), error -> context.sendMessage("The user with the ID of `{0}` is not banned.", argument.get(0)));
    }
}
