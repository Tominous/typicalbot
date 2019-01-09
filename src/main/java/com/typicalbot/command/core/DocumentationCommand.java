/**
 * Copyright 2016-2018 Bryan Pikaard & Nicholas Sylke
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

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.shard.Shard;
import com.typicalbot.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;

@CommandConfiguration(category = CommandCategory.CORE, aliases = {"documentation", "docs"})
public class DocumentationCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            context.sendMessage("Incorrect usage.");
            return;
        }

        Command command = Shard.getSingleton().getCommandManager().findCommand(argument.get(0));

        if (command == null) {
            context.sendMessage("That command doesn't exist.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Documentation for " + argument.get(0));
        builder.addField("Category", StringUtil.firstUpperCase(command.getConfiguration().category().name()), true);
        builder.addField("Aliases", String.join(", ", command.getConfiguration().aliases()), true);
        builder.addField("Permission", command.permission().name(), true);
        builder.addField("Usage", String.join("\n", command.usage()), false);
        builder.addField("Description", command.description(), false);

        context.sendEmbed(builder.build());
    }
}
