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

import com.sun.management.OperatingSystemMXBean;
import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandCheck;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.nxt.shard.ShardManager;
import com.typicalbot.util.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.Permission;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

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
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        OperatingSystemMXBean bean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long usedJVMMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() >> 20;
        long totalJVMMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax() >> 20;
        String os = String.format("%s %s %s", bean.getName(), bean.getArch(), bean.getVersion());

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("TypicalBot Statistics");
        builder.setColor(Color.TYPICALBOT_BLUE.rgb());
        builder.setThumbnail("https://cdn.discordapp.com/icons/509030978484699136/ce37733019bb77ecaed550dfdaacea89.png");

        builder.addField("Uptime", UptimeCommand.getUptime(), true);
        builder.addField("Servers", Integer.toString(ShardManager.getGuildCount()), true);
        builder.addField("Channels", Integer.toString(ShardManager.getChannelCount()), true);
        builder.addField("Users", Integer.toString(ShardManager.getUserCount()), true);
        builder.addField("Voice Connections", Integer.toString(ShardManager.getVoiceConnectionCount()), true);
        builder.addField("Operating System", os, true);
        builder.addField("CPU Usage", new DecimalFormat("###.###%").format(bean.getProcessCpuLoad()), true);
        builder.addField("RAM Usage", String.format("%dMB/%dMB", usedJVMMemory, totalJVMMemory), true);
        builder.addField("Threads", String.format("%d/%d", Thread.activeCount(), Thread.getAllStackTraces().size()), true);
        builder.addField("Library", "JDA v" + JDAInfo.VERSION, true);
        builder.addField("Maintainers", "HyperCoder#2975\nNick#4490", true);

        context.sendEmbed(builder.build());
    }
}

