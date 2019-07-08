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

import com.typicalbot.nxt.command.*;
import net.dv8tion.jda.api.entities.User;

@CommandConfiguration(category = CommandCategory.INTERACTION, aliases = "poke")
public class PokeCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "poke [@user]"
        };
    }

    @Override
    public String description() {
        return "Poke another user.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        User target = context.getMessage().getAuthor();

        if (argument.has()) {
            User temp = context.getUser(argument.get(0));

            if (temp != null) {
                target = temp;
            }
        }

        if (target == context.getAuthor()) {
            context.sendMessage("You can't poke yourself.");
        } else {
            context.sendMessage(context.getAuthor().getName() + " poked " + target.getName() + "!");
        }
    }
}
