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
package com.typicalbot.command.core;

import com.typicalbot.command.CommandPermission;
import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.shard.Shard;
import com.typicalbot.util.StringUtil;
import net.dv8tion.jda.core.EmbedBuilder;

@CommandConfiguration(category = CommandCategory.CORE, aliases = {"help", "?", "info", "information", "docs", "documentation"})
public class HelpCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
                "help",
                "help [command]"
        };
    }

    @Override
    public String description() {
        return "Get general information about TypicalBot or help with a specific command.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            // TODO(nsylke): Make this an embed?
            context.sendMessage("Hello there, I'm TypicalBot! I am developed by HyperCoder#2975 and Nick#4490 with the help of their fantastic staff team. If you would like to access my list of commands, try using `b$commands`. If you need any help with commands or settings, you can find documentation at <https://typicalbot.com/documentation>. If you cannot figure out how to use a command or setting, or would like to chat, you can join us in the TypicalBot Lounge at <https://www.typicalbot.com/join-us>.\n\nBuilt upon years of experience, TypicalBot is the ironically-named bot that is far from typical. Stable, lightning fast, and easy to use, TypicalBot is there for you and will seamlessly help you moderate your server and offer some entertaining features for your users, every step of the way.");
            return;
        }

        Command command = Shard.getSingleton().getCommandManager().findCommand(argument.get(0));

        if (command == null) {
            // TODO(nsylke): Make this an embed?
            context.sendMessage("That command does not exist.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        // TODO(nsylke): Add a color to the embed.
        builder.setTitle("Documentation for " + argument.get(0));
        builder.addField("Category", StringUtil.capitalize(command.getConfiguration().category().name()), true);
        builder.addField("Aliases", String.join(", ", command.getConfiguration().aliases()), true);
        builder.addField("Permission", command.permission().name(), true);
        builder.addField("Usage", String.join("\n", command.usage()), false);
        builder.addField("Description", command.description(), false);

        context.sendEmbed(builder.build());
    }
}
