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
package com.typicalbot.events;

import com.typicalbot.common.Container;
import com.typicalbot.common.command.CommandManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageEvent extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor() == null || event.getAuthor().isBot() || event.getAuthor().equals(event.getJDA().getSelfUser())) return;
        if (!event.getGuild().isAvailable()) return;
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) return;

        if (event.getMessage().getContentRaw().matches("^<@!?" + event.getJDA().getSelfUser().getId() + ">$")) {
            // Change prefix to use configuration.
            event.getChannel().sendMessage("The server's prefix is `v$`.").complete();
        }

        // Change prefix to use configuration.
        String prefix = "v$";

        if (event.getMessage().getContentRaw().startsWith(prefix)) {
            // Temporary.
            Container.get(CommandManager.class).process(event.getMessage(), event.getMember(), event.getChannel(), event.getGuild());
        }

        // Do something else...
    }
}
