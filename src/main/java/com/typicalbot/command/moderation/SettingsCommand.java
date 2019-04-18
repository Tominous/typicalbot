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
import net.dv8tion.jda.core.entities.Role;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = {"settings", "set"})
public class SettingsCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "settings list",
            "settings view [setting]",
            "settings edit [setting]"
        };
    }

    @Override
    public String description() {
        return "View or customize your servers setting and enable/disable specific features.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_ADMINISTRATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkArguments(argument);

        String command = argument.get(0);

        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();

        switch (command) {
            case "list":
                context.sendMessage("Available settings:\n\n - adminrole\n - modrole");
                return;
            case "view":
                switch (argument.get(1)) {
                    case "adminrole":
                        context.sendMessage("The current value for `adminrole` is `{0}`.", String.valueOf(object.getGuildSettings().getRoles().getAdminRole()));
                        return;
                    case "modrole":
                        context.sendMessage("The current value for `modrole` is `{0}`.", String.valueOf(object.getGuildSettings().getRoles().getModeratorRole()));
                        return;
                    default:
                        context.sendMessage("Couldn't find that setting.");
                        return;
                }
            case "edit":
                switch (argument.get(1)) {
                    case "adminrole":
                        Role adminrole = context.getRole(argument.get(2));

                        if (adminrole == null) {
                            context.sendMessage("The role specified couldn't be found.");
                            return;
                        }

                        object.getGuildSettings().getRoles().setAdminRole(adminrole.getIdLong());
                        dao.update(object);
                        context.sendMessage("Successfully updated to `adminrole` to `{0}`.", String.valueOf(adminrole.getIdLong()));
                        return;
                    case "modrole":
                        Role modrole = context.getRole(argument.get(2));

                        if (modrole == null) {
                            context.sendMessage("The role specified couldn't be found.");
                            return;
                        }

                        object.getGuildSettings().getRoles().setModeratorRole(modrole.getIdLong());
                        dao.update(object);
                        context.sendMessage("Successfully updated to `modrole` to `{0}`.", String.valueOf(modrole.getIdLong()));
                        return;
                    default:
                        context.sendMessage("Couldn't find that setting.");
                        return;
                }
            default:
                context.sendMessage("Incorrect usage. Please check `$help settings` for usage.");
        }
    }
}
