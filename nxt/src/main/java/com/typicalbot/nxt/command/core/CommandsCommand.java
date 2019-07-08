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
package com.typicalbot.nxt.command.core;

import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.nxt.data.mongo.dao.GuildDAO;
import com.typicalbot.nxt.data.mongo.objects.GuildObject;
import com.typicalbot.nxt.shard.Shard;
import com.typicalbot.nxt.util.StringUtil;
import com.typicalbot.util.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@CommandConfiguration(category = CommandCategory.CORE, aliases = {"commands", "cmds"})
public class CommandsCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "commands"
        };
    }

    @Override
    public String description() {
        return "Receive a list of TypicalBot commands.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();

        if (object.getGuildSettings().isDmCommand()) {
            context.getAuthor().openPrivateChannel().queue(c -> c.sendMessage(getCommands()).queue());
            context.sendMessage("{0}, we have sent a list of commands via direct message.", context.getMember().getAsMention());
        } else {
            context.sendEmbed(getCommands());
        }
    }

    private MessageEmbed getCommands() {
        Set<Command> commands = Shard.getSingleton().getCommandManager().getCommands();

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("TypicalBot Commands");
        builder.setColor(Color.TYPICALBOT_BLUE.rgb());

        for (CommandCategory category : CommandCategory.values()) {
            if (category != CommandCategory.SYSTEM) {
                builder.addField(StringUtil.capitalize(category.name()), commands.stream()
                    .filter(c -> c.getConfiguration().category().equals(category) && c.getConfiguration().category() != CommandCategory.SYSTEM)
                    .sorted(Comparator.comparing((Command x) -> x.getConfiguration().aliases()[0]).thenComparing((Command y) -> y.getConfiguration().aliases()[0]))
                    .map(c -> c.getConfiguration().aliases()[0])
                    .collect(Collectors.joining(", ")), false);
            }
        }

        return builder.build();
    }
}
