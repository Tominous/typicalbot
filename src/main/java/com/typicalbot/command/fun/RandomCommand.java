/**
 * Copyright 2016-2019 Bryan Pikaard & Nicholas Sylke
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

import java.util.Random;

@CommandConfiguration(category = CommandCategory.FUN, aliases = "random")
public class RandomCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (argument.getArguments().size() == 1) {
            int min;

            try {
                min = Integer.parseInt(argument.get(0));
            } catch (NumberFormatException ex) {
                min = 0;
            }

            context.sendMessage(Integer.toString(new Random().ints(0, min).iterator().nextInt()));
        } else if (argument.getArguments().size() >= 2) {
            int min;
            int max;

            try {
                min = Integer.parseInt(argument.get(0));
                max = Integer.parseInt(argument.get(1));
            } catch (NumberFormatException ex) {
                min = 0;
                max = Integer.MAX_VALUE;
            }

            context.sendMessage(Integer.toString(new Random().ints(min, max).iterator().nextInt()));
        } else {
            context.sendMessage(Integer.toString(new Random().ints(0, Integer.MAX_VALUE).iterator().nextInt()));
        }
    }
}
