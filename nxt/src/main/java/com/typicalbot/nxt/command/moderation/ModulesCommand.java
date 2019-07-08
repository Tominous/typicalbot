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

import com.typicalbot.nxt.command.*;
import com.typicalbot.nxt.data.mongo.dao.GuildDAO;
import com.typicalbot.nxt.data.mongo.objects.GuildObject;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "modules")
public class ModulesCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_ADMINISTRATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            context.sendMessage("Incorrect usage.");
            return;
        }

        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();
        boolean option = Boolean.parseBoolean(argument.get(1));

        switch (argument.get(0)) {
            case "fun":
                object.getGuildSettings().getModules().setFun(option);
                break;
            case "integration":
                object.getGuildSettings().getModules().setIntegration(option);
                break;
            case "interaction":
                object.getGuildSettings().getModules().setInteraction(option);
                break;
            case "miscellaneous":
                object.getGuildSettings().getModules().setMiscellaneous(option);
                break;
            case "moderation":
                object.getGuildSettings().getModules().setModeration(option);
                break;
            case "music":
                object.getGuildSettings().getModules().setMusic(option);
                break;
            case "utility":
                object.getGuildSettings().getModules().setUtility(option);
                break;
            default:
                context.sendMessage("Invalid module.");
                return;
        }

        dao.update(object);
        context.sendMessage("Successfully updated {0) module.", argument.get(0));
    }
}
