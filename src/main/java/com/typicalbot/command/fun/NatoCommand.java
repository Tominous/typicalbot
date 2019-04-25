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

import java.util.HashMap;
import java.util.Map;

@CommandConfiguration(category = CommandCategory.FUN, aliases = "nato")
public class NatoCommand implements Command {
    private Map<Character, String> mapping = new HashMap<>();

    {
        mapping.put('a', "Alfa");
        mapping.put('b', "Bravo");
        mapping.put('c', "Charlie");
        mapping.put('d', "Delta");
        mapping.put('e', "Echo");
        mapping.put('f', "Foxtrot");
        mapping.put('g', "Golf");
        mapping.put('h', "Hotel");
        mapping.put('i', "India");
        mapping.put('j', "Juliett");
        mapping.put('k', "Kilo");
        mapping.put('l', "Lima");
        mapping.put('m', "Mike");
        mapping.put('n', "November");
        mapping.put('o', "Oscar");
        mapping.put('p', "Papa");
        mapping.put('q', "Quebec");
        mapping.put('r', "Romeo");
        mapping.put('s', "Sierra");
        mapping.put('t', "Tango");
        mapping.put('u', "Uniform");
        mapping.put('v', "Victor");
        mapping.put('w', "Whiskey");
        mapping.put('x', "X-ray");
        mapping.put('y', "Yankee");
        mapping.put('z', "Zulu");
        mapping.put('1', "One");
        mapping.put('2', "Two");
        mapping.put('3', "Three");
        mapping.put('4', "Four");
        mapping.put('5', "Five");
        mapping.put('6', "Six");
        mapping.put('7', "Seven");
        mapping.put('8', "Eight");
        mapping.put('9', "Nine");
        mapping.put('0', "Zero");
        mapping.put(' ', "");
    }

    @Override
    public String[] usage() {
        return new String[]{
            "nato [text]"
        };
    }

    @Override
    public String description() {
        return "Translate text into the nato alphabet.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkArguments(argument);

        StringBuilder builder = new StringBuilder();

        for (char c : argument.toString().toLowerCase().toCharArray()) {
            builder.append(mapping.get(c)).append(" ");
        }

        context.sendMessage(builder.toString());
    }
}
