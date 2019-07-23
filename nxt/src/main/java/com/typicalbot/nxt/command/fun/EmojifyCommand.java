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
package com.typicalbot.nxt.command.fun;

import com.typicalbot.command.*;

import java.util.HashMap;
import java.util.Map;

@CommandConfiguration(category = CommandCategory.FUN, aliases = "emojify")
public class EmojifyCommand implements Command {
    private Map<Character, String> mapping = new HashMap<>();

    {
        mapping.put('a', ":regional_indicator_a:");
        mapping.put('b', ":regional_indicator_b:");
        mapping.put('c', ":regional_indicator_c:");
        mapping.put('d', ":regional_indicator_d:");
        mapping.put('e', ":regional_indicator_e:");
        mapping.put('f', ":regional_indicator_f:");
        mapping.put('g', ":regional_indicator_g:");
        mapping.put('h', ":regional_indicator_h:");
        mapping.put('i', ":regional_indicator_i:");
        mapping.put('j', ":regional_indicator_j:");
        mapping.put('k', ":regional_indicator_k:");
        mapping.put('l', ":regional_indicator_l:");
        mapping.put('m', ":regional_indicator_m:");
        mapping.put('n', ":regional_indicator_n:");
        mapping.put('o', ":regional_indicator_o:");
        mapping.put('p', ":regional_indicator_p:");
        mapping.put('q', ":regional_indicator_q:");
        mapping.put('r', ":regional_indicator_r:");
        mapping.put('s', ":regional_indicator_s:");
        mapping.put('t', ":regional_indicator_t:");
        mapping.put('u', ":regional_indicator_u:");
        mapping.put('v', ":regional_indicator_v:");
        mapping.put('w', ":regional_indicator_w:");
        mapping.put('x', ":regional_indicator_x:");
        mapping.put('y', ":regional_indicator_y:");
        mapping.put('z', ":regional_indicator_z:");
        mapping.put('1', ":one:");
        mapping.put('2', ":two:");
        mapping.put('3', ":three:");
        mapping.put('4', ":four:");
        mapping.put('5', ":five:");
        mapping.put('6', ":six:");
        mapping.put('7', ":seven:");
        mapping.put('8', ":eight:");
        mapping.put('9', ":nine:");
        mapping.put('0', ":zero:");
        mapping.put('!', ":grey_exclamation:");
        mapping.put('?', ":grey_question:");
        mapping.put('#', ":hash:");
        mapping.put('*', ":asterisk:");
        mapping.put(' ', " ");
    }

    @Override
    public String[] usage() {
        return new String[]{
            "emoji [text]"
        };
    }

    @Override
    public String description() {
        return "Translate your sentence into emojis.";
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
            builder.append(mapping.get(c));
        }

        context.sendMessage(builder.toString());
    }
}
