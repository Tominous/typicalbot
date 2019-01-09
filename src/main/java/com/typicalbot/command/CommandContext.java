/**
 * Copyright 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.command;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class CommandContext {
    private final Message message;

    public CommandContext(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return this.message;
    }

    public void sendMessage(String message, Object... params) {
        this.message.getChannel().sendMessage(String.format(message, params)).queue();
    }

    public void sendEmbed(MessageEmbed embed) {
        this.message.getChannel().sendMessage(embed).queue();
    }

    public GuildChannel getChannel(String channel) {
        if (!this.message.getMentionedChannels().isEmpty()) {
            return this.message.getMentionedChannels().get(0);
        }

        return this.message.getGuild().getChannels().stream().filter(ch -> ch.getName().equalsIgnoreCase(channel)).findFirst().orElse(this.message.getGuild().getChannels().stream().filter(ch -> ch.getId().equalsIgnoreCase(channel)).findFirst().orElse(null));
    }

    public Role getRole(String role) {
        if (!this.message.getMentionedRoles().isEmpty()) {
            return this.message.getMentionedRoles().get(0);
        }

        List<Role> roles = this.message.getGuild().getRolesByName(role, true);
        return roles.isEmpty() ? this.message.getGuild().getRoleById(role) : roles.get(0);
    }
}
