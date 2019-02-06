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
package com.typicalbot.command.fun;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.core.entities.User;

@CommandConfiguration(category = CommandCategory.FUN, aliases = "cookie")
public class CookieCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
                "cookie",
                "cookie [user]"
        };
    }

    @Override
    public String description() {
        return "Give another user a cookie or keep them all for yourself.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            String addon = Math.random() <= 0.25 ? "laughed like a madman while slowly eating the cookies they kept for themselves in front of everyone." : "decided to keep all of the cookies for themselves! What a jerk! :angry:";

            context.sendMessage("{0} {1}", context.getMessage().getAuthor().getName(), addon);
            return;
        }

        User target = context.getUser(argument.get(0));

        if (target == null) {
            context.sendMessage("The user specified does not exist.");
        }

        context.sendMessage("{0} just gave {1} a cookie! :cookie:", context.getMessage().getAuthor().getName(), target.getName());
    }
}
