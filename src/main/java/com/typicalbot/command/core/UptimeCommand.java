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
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@CommandConfiguration(triggers = {"uptime"})
public class UptimeCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long seconds = rb.getUptime() / 1000;
        long d = (long) Math.floor(seconds / 86400);
        long h = (long) Math.floor((seconds % 86400) / 3600);
        long m = (long) Math.floor(((seconds % 86400) % 3600) / 60);
        long s = (long) Math.floor(((seconds % 86400) % 3600) % 60);

        StringBuilder builder = new StringBuilder();

        // TODO(nsylke): Pluralization.
        if (d > 0) builder.append(d + " days, ");
        if (h > 0) builder.append(h + " hours, ");
        if (m > 0) builder.append(m + " minutes, ");
        builder.append(s + " seconds");

        context.sendMessage("Uptime: %s", builder.toString());
    }

    @Override
    public void embed(CommandContext context, CommandArgument argument) {
    }
}
