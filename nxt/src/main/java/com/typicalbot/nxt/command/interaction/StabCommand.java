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
package com.typicalbot.nxt.command.interaction;

import com.typicalbot.command.*;
import net.dv8tion.jda.api.entities.User;

import java.util.Random;

@CommandConfiguration(category = CommandCategory.INTERACTION, aliases = {"stab", "slash"})
public class StabCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "stab <@mention>",
            "stab"
        };
    }

    @Override
    public String description() {
        return "'Stabs' the mentioned user.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        User author = context.getMessage().getAuthor();

        String[] options = {"", "Someone call the police! :oncoming_police_car:"};
        Random rand = new Random();
        int x = rand.nextInt(options.length);

        if (!argument.has()) {
            context.sendMessage("{0} stabbed themselves! :dagger::scream: {1}", author.getAsMention(), options[x]);
            return;
        }

        User mention = context.getUser(argument.get(0));

        if (mention.equals(author)) {
            context.sendMessage("{0} stabbed themselves! :dagger::scream: {1}", author.getAsMention(), options[x]);
            return;
        } else if (mention == null) {
            context.sendMessage("{0}, the specified user does not exist. Try again.", author.getAsMention());
            return;
        }

        context.sendMessage("{0} just stabbed {1}! :dagger::scream: {2}", author.getAsMention(), mention.getAsMention(), options[x]);
    }
}
