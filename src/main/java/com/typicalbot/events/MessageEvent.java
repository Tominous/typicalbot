package com.typicalbot.events;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageEvent extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor() == null || event.getAuthor().isBot() || event.getAuthor().equals(event.getJDA().getSelfUser())) return;
        if (!event.getGuild().isAvailable()) return;
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) return;

        // Do something else...
    }
}
