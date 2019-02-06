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

import java.util.TreeMap;

@CommandConfiguration(category = CommandCategory.FUN, aliases = {"roman", "romannumerals"})
public class RomanCommand implements Command {
    private TreeMap<Integer, String> mapping = new TreeMap<>();

    {
        mapping.put(1000, "M");
        mapping.put(900, "CM");
        mapping.put(500, "D");
        mapping.put(400, "CD");
        mapping.put(100, "C");
        mapping.put(90, "XC");
        mapping.put(50, "L");
        mapping.put(40, "XL");
        mapping.put(10, "X");
        mapping.put(9, "IX");
        mapping.put(5, "V");
        mapping.put(4, "IV");
        mapping.put(1, "I");
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            context.sendMessage("You can't translate nothing into nato...");
            return;
        }

        int number;

        try {
            number = Integer.parseInt(argument.get(0));
        } catch (NumberFormatException ex) {
            context.sendMessage("Cannot parse input.");
            return;
        }

        if (number == 0) {
            context.sendMessage("Cannot parse input.");
            return;
        }

        context.sendMessage(getRomanValue(number));
    }

    private String getRomanValue(int number) {
        int j = mapping.floorKey(number);

        if (number == j) {
            return mapping.get(number);
        }

        return mapping.get(j) + getRomanValue(number - j);
    }
}
