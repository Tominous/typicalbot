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
package com.typicalbot.nxt.command.miscellaneous;

import com.typicalbot.command.*;

@CommandConfiguration(category = CommandCategory.MISCELLANEOUS, aliases = "say")
public class SayCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "say [message]"
        };
    }

    @Override
    public String description() {
        return "Have the bot say something.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkArguments(argument);

        // Delete authors message
        context.getMessage().delete().queue();
        context.sendMessage(argument.toString().replaceAll("@here", "@\u200bhere").replaceAll("@everyone", "@\u200beveryone"));
    }
}
