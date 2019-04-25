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
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.data.mongo.dao.GuildDAO;
import com.typicalbot.data.mongo.objects.GuildObject;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "mute")
public class MuteCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "mute [@user]"
        };
    }

    @Override
    public String description() {
        return "Mute a member in the server.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getMember(), Permission.MANAGE_ROLES);
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MANAGE_ROLES);
        CommandCheck.checkArguments(argument);

        User temp = context.getUser(argument.get(0));
        if (temp == null) {
            context.sendMessage("The user `{0}` does not exist.", argument.get(0));
            return;
        }

        if (!context.getMember().canInteract(context.getGuild().getMember(temp))) {
            context.sendMessage("You do not have permission to mute that user.");
            return;
        }

        if (!context.getSelfMember().canInteract(context.getGuild().getMember(temp))) {
            context.sendMessage("TypicalBot does not have permission to mute that user.");
            return;
        }

        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getRoles().getMuteRole() == 0L) {
            context.sendMessage("The mute role is not setup for the server yet.");
            return;
        }

        Role role = context.getRole(Long.toString(object.getGuildSettings().getRoles().getMuteRole()));
        if (role == null) {
            context.sendMessage("The mute role does not exist or not setup for the server yet.");
            return;
        }

        context.getGuild().getController().addSingleRoleToMember(context.getGuild().getMember(temp), role).queue(o -> {
            context.sendMessage("Successfully muted {0}.", temp.getAsTag());
        });
    }
}
