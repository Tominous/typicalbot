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
import net.dv8tion.jda.core.entities.User;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "voicemute")
public class VoicemuteCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            context.sendMessage("Incorrect usage.");
            return;
        }

        User temp = context.getUser(argument.get(0));

        if (temp == null) {
            context.sendMessage("Couldn't find that user.");
            return;
        }

        if (!context.getGuild().getMember(temp).getVoiceState().inVoiceChannel()) {
            context.sendMessage("User is not in voice channel.");
            return;
        }

        context.getGuild().getController().setMute(context.getGuild().getMember(temp), true).queue(o -> context.sendMessage("Successfully muted {0}", temp.getAsTag()));
    }
}
