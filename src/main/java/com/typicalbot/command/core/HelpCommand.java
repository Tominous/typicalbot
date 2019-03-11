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
package com.typicalbot.command.core;

import com.typicalbot.command.CommandPermission;
import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.config.Config;
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
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("TypicalBot");
            builder.setDescription("Thanks for using TypicalBot, a multi-threaded and high-performance Discord bot. TypicalBot is stable, lightning fast, reliable and easy to use.");
            builder.setColor(CommandContext.TYPICALBOT_BLUE);

            builder.addField("Website", "[Go to Website](https://typicalbot.com)", true);
            builder.addField("Documentation", "[Go to Documentation](https://typicalbot.com/documentation)", true);
            builder.addBlankField(true);
            builder.addField("Source code", "[Go to GitHub](https://github.com/typicalbot/typicalbot)", true);
            builder.addField("Support", "[Go to TypicalBot Lounge](https://discord.gg/typicalbot)", true);
            builder.addBlankField(true);

            context.sendEmbed(builder.build());
            return;
        }

        Command command = Shard.getSingleton().getCommandManager().findCommand(argument.get(0));

        if (command == null) {
            context.sendMessage("Whoops! Looks like the command that you are looking for does not exist.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Documentation for " + argument.get(0));
        builder.setDescription(command.description());
        builder.setColor(CommandContext.TYPICALBOT_BLUE);

        builder.addField("Category", StringUtil.capitalize(command.getConfiguration().category().name()), true);
        builder.addField("Permission", StringUtil.capitalize(command.permission().name().replace('_', ' ')), true);
        builder.addBlankField(true);
        builder.addField("Aliases", String.join(", ", command.getConfiguration().aliases()), false);
        builder.addField("Usage", String.join("\n", command.usage()), false);

        context.sendEmbed(builder.build());
    }
}
