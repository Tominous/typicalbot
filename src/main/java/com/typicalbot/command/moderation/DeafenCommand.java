/*
 * Copyright 2019 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
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

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "deafen")
public class DeafenCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!context.getMember().hasPermission(Permission.VOICE_DEAF_OTHERS)) {
            context.sendMessage("You do not have permission to deafen members.");
            return;
        }

        if (!context.getSelfMember().hasPermission(Permission.VOICE_DEAF_OTHERS)) {
            context.sendMessage("TypicalBot does not have permission to deafen members.");
            return;
        }

        if (!argument.has()) {
            context.sendMessage("Incorrect usage. Please check `$help deafen` for usage.");
            return;
        }

        User temp = context.getUser(argument.get(0));
        if (temp == null) {
            context.sendMessage("The user `{0}` does not exist.", argument.get(0));
            return;
        }

        if (!context.getGuild().getMember(temp).getVoiceState().inVoiceChannel()) {
            context.sendMessage("User is not in voice channel.");
            return;
        }

        if (!context.getMember().canInteract(context.getGuild().getMember(temp))) {
            context.sendMessage("You do not have permission to deafen that user.");
            return;
        }

        if (!context.getSelfMember().canInteract(context.getGuild().getMember(temp))) {
            context.sendMessage("TypicalBot does not have permission to deafen that user.");
            return;
        }

        context.getGuild().getController().setDeafen(context.getGuild().getMember(temp), true).queue(o -> context.sendMessage("Successfully deafen {0}.", temp.getAsTag()));
    }
}
