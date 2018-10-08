/**
 * TypicalBot - A multipurpose discord bot
 * Copyright (C) 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
