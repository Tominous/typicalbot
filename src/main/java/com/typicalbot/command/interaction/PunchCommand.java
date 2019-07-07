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
package com.typicalbot.command.interaction;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.api.entities.User;

import java.util.Random;

@CommandConfiguration(category = CommandCategory.INTERACTION, aliases = "punch")
public class PunchCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "punch",
            "punch [@user]"
        };
    }

    @Override
    public String description() {
        return "Punch yourself or another person.";
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

        String[] addons = new String[]{
            "Oh, dang! Right to the jaw! That must've hurt!"
        };

        if (target == context.getMessage().getAuthor()) {
            context.sendMessage(target.getName() + ", stop hitting yourself! :punch:");
        } else {
            context.sendMessage(context.getMessage().getAuthor().getName() + " just punched " + target.getName() + "! :punch: " + addons[new Random().nextInt(addons.length)]);
        }
    }
}
