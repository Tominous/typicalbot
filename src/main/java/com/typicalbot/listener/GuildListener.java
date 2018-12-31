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
package com.typicalbot.listener;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // TODO(nsylke): Check to see if we are missing anything... maybe event.getAuthor().isFake() ?!?!
        if (event.getAuthor() == null || event.getAuthor().isBot()) return;
        if (!event.getGuild().isAvailable()) return;
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) return;

        String rawMessage = event.getMessage().getContentRaw();

        if (rawMessage.matches("^<@!?" + event.getJDA().getSelfUser().getId() + ">$")) {
            // TODO(nsylke): Show default prefix from configuration if setting in database is not set.
            event.getChannel().sendMessage("The server's prefix is `b$`.").queue();
            return;
        }

        // TODO(nsylke): Commands
    }
}
