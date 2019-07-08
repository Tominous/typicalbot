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
package com.typicalbot.nxt.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import java.text.MessageFormat;
import java.util.List;

public class CommandContext {
    public static final int TYPICALBOT_BLUE = 0x1976D2;
    public static final int TYPICALBOT_SUCCESS = 0x00FF00;
    public static final int TYPICALBOT_ERROR = 0xFF0000;

    private final Message message;

    public CommandContext(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return this.message;
    }

    public Guild getGuild() {
        return this.message.getGuild();
    }

    public MessageChannel getChannel() {
        return this.message.getChannel();
    }

    public User getAuthor() {
        return this.message.getAuthor();
    }

    public Member getMember() {
        return this.message.getMember();
    }

    public User getSelfUser() {
        return this.message.getJDA().getSelfUser();
    }

    public Member getSelfMember() {
        return this.message.getGuild().getSelfMember();
    }

    public JDA getJDA() {
        return this.message.getJDA();
    }

    public void sendMessage(String message, Object... params) {
        this.getChannel().sendMessage(MessageFormat.format(message, params)).queue();
    }

    public void sendEmbed(MessageEmbed embed) {
        this.getChannel().sendMessage(embed).queue();
    }

    public User getUser(String user) {
        if (!this.message.getMentionedUsers().isEmpty()) {
            return this.message.getMentionedUsers().get(0);
        }

        Member member = this.getGuild().getMembers().stream()
            .filter(m -> m.getUser().getName().equalsIgnoreCase(user))
            .findFirst()
            .orElse(this.getGuild().getMembers().stream()
                .filter(m -> m.getUser().getId().equalsIgnoreCase(user))
                .findFirst()
                .orElse(null));

        return (member != null) ? member.getUser() : null;
    }

    public GuildChannel getChannel(String channel) {
        if (!this.message.getMentionedChannels().isEmpty()) {
            return this.message.getMentionedChannels().get(0);
        }

        return this.getGuild().getChannels().stream()
            .filter(ch -> ch.getName().equalsIgnoreCase(channel))
            .findFirst()
            .orElse(this.getGuild().getChannels().stream()
                .filter(ch -> ch.getId().equalsIgnoreCase(channel))
                .findFirst()
                .orElse(null));
    }

    public Role getRole(String role) {
        if (!this.message.getMentionedRoles().isEmpty()) {
            return this.message.getMentionedRoles().get(0);
        }

        List<Role> roles = this.getGuild().getRolesByName(role, true);
        return roles.isEmpty() ? this.getGuild().getRoleById(role) : roles.get(0);
    }
}
