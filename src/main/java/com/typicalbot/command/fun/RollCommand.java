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
package com.typicalbot.command.fun;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@CommandConfiguration(category = CommandCategory.FUN, aliases = {"roll", "dice"})
public class RollCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "roll [dice]d[sides]"
        };
    }

    @Override
    public String description() {
        return "Roll some dice.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkArguments(argument);

        String[] values = argument.get(0).split("d");

        if (values.length != 2) {
            context.sendMessage("Incorrect usage.");
            return;
        }

        int dices;
        int sides;

        try {
            dices = Integer.parseInt(values[0]);
            sides = Integer.parseInt(values[1]);
        } catch (NumberFormatException ex) {
            dices = 2;
            sides = 6;
        }

        if (dices == 0 || dices > 100) {
            context.sendMessage("Dices must be in between 0 and 100.");
            return;
        }

        if (sides == 0 || sides > 1000) {
            context.sendMessage("Sides must be in between 0 and 1000.");
            return;
        }

        int[] rolls = new int[dices];

        for (int i = 0; i < dices; i++) {
            rolls[i] = (new Random().ints(1, sides).iterator().nextInt());
        }

        context.sendMessage("You rolled: {0}. Total: {1}", IntStream.of(rolls).mapToObj(Integer::toString).collect(Collectors.joining(", ")), IntStream.of(rolls).sum());
    }
}
