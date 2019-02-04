/**
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
package com.typicalbot.command.core;

import com.sun.management.OperatingSystemMXBean;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.shard.Shard;
import net.dv8tion.jda.core.EmbedBuilder;

import java.lang.management.ManagementFactory;

@CommandConfiguration(category = CommandCategory.CORE, aliases = {"statistics", "stats"})
public class StatisticsCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "statistics"
        };
    }

    @Override
    public String description() {
        return "Get statistics about TypicalBot.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        OperatingSystemMXBean os = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("TypicalBot Statistics");

        builder.addField("Servers", Integer.toString(Shard.getSingleton().getGuilds()), true);
        builder.addField("Channels", Integer.toString(Shard.getSingleton().getInstance().getTextChannels().size() + Shard.getSingleton().getInstance().getVoiceChannels().size()), true);
        builder.addField("Users", Integer.toString(Shard.getSingleton().getUsers()), true);
        builder.addField("CPU Usage", String.format("%.2f", (os.getProcessCpuLoad() * 100)) + "%", true);
        builder.addField("Library", "JDA", true);
        builder.addField("Created by", "HyperCoder#2975\nNick#4490", true);

        context.sendEmbed(builder.build());
    }
}

