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

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;

import java.lang.management.ManagementFactory;

@CommandConfiguration(category = CommandCategory.CORE, aliases = "uptime")
public class UptimeCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "uptime"
        };
    }

    @Override
    public String description() {
        return "Check to see how long TypicalBot has been online for.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        context.sendMessage("TypicalBot has been online for {0}.", getUptime());
    }

    public static String getUptime() {
        long time = ManagementFactory.getRuntimeMXBean().getUptime();

        long days = time / 86400000L % 30;
        long hours = time / 3600000L % 24;
        long minutes = time / 60000L % 60;
        long seconds = time / 1000L % 60;

        StringBuilder builder = new StringBuilder();
        if (days != 0) {
            builder.append(days).append(" ").append(days > 1 ? "days" : "day").append(", ");
        }

        if (hours != 0) {
            builder.append(hours).append(" ").append(hours > 1 ? "hours" : "hour").append(", ");
        }

        if (minutes != 0) {
            builder.append(minutes).append(" ").append(minutes > 1 ? "minutes" : "minute").append(" and ");
        }

        builder.append(seconds).append(" ").append(seconds > 1 ? "seconds" : "second");

        return builder.toString();
    }
}
