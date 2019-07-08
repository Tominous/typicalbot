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
package com.typicalbot.nxt.command.system;

import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import net.dv8tion.jda.api.EmbedBuilder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@CommandConfiguration(category = CommandCategory.SYSTEM, aliases = "eval")
public class EvalCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.TYPICALBOT_ADMINISTRATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (context.getMessage().getAuthor().getIdLong() != 187342661060001792L) return;

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        engine.put("context", context);
        engine.put("argument", argument);

        try {
            Object result = engine.eval(argument.toString());

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("TypicalBot Eval");
            builder.addField("Input", "```" + argument.toString() + "```", false);
            builder.addField("Output", "```" + result + "```", false);

            context.sendEmbed(builder.build());
        } catch (ScriptException e) {
            context.sendMessage("An error occurred.");
        }
    }
}
