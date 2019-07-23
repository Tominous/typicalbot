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

import com.typicalbot.command.*;
import com.typicalbot.nxt.data.mongo.dao.GuildDAO;
import com.typicalbot.nxt.data.mongo.objects.GuildObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "take")
public class TakeCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "take [role]"
        };
    }

    @Override
    public String description() {
        return "Take a role from you a role off of the public roles list.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MANAGE_ROLES);
        CommandCheck.checkArguments(argument);

        if (!context.getSelfMember().canInteract(context.getMember())) {
            context.sendMessage("TypicalBot does not have permission to manage roles for that user.");
            return;
        }

        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getRoles().getPublicRoles() == null) {
            context.sendMessage("There are no public roles for this server.");
            return;
        }

        Role role = context.getRole(argument.get(0));

        if (role == null) {
            context.sendMessage("The role specified does not exist.");
            return;
        }

        for (long id : object.getGuildSettings().getRoles().getPublicRoles()) {
            if (role.getIdLong() == id) {
                context.getGuild().removeRoleFromMember(context.getMember(), role).queue(o -> context.sendMessage("Successfully taken {0} to {1}.", role.getName(), context.getAuthor().getAsTag()));
                break;
            }
        }
    }
}