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
package com.typicalbot.command.utility;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.data.mongo.dao.GuildDAO;
import com.typicalbot.data.mongo.objects.GuildObject;
import net.dv8tion.jda.core.entities.Role;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = "unsubscribe")
public class UnsubscribeCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getRoles().getSubscriberRole() == 0L) {
            context.sendMessage("There is no subscriber role set up yet.");
            return;
        }

        Role role = context.getGuild().getRoleById(object.getGuildSettings().getRoles().getSubscriberRole());

        if (role == null) {
            context.sendMessage("There is no subscriber role set up yet.");
            return;
        }

        if (!context.getMember().getRoles().contains(role)) {
            context.sendMessage("You are not subscribed.");
            return;
        }

        context.getGuild().getController().removeRolesFromMember(context.getGuild().getMember(context.getMessage().getAuthor()), role).queue();
        context.sendMessage("Success! You are now unsubscribed.");
    }
}
