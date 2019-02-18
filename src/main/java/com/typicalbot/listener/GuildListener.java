/*
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
package com.typicalbot.listener;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandContext;
import com.typicalbot.config.Config;
import com.typicalbot.data.mongo.dao.GuildDAO;
import com.typicalbot.data.mongo.objects.GuildObject;
import com.typicalbot.shard.Shard;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GuildListener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuildListener.class);

    private GuildDAO guildDAO = new GuildDAO();

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        GuildObject guild = guildDAO.create(event.getGuild());
        guildDAO.update(guild);

        LOGGER.info("Joined guild {} with {} users", event.getGuild().getName(), event.getGuild().getMembers().size());
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        if (guildDAO.get(event.getGuild().getIdLong()).isPresent()) {
            GuildObject object = guildDAO.get(event.getGuild().getIdLong()).get();
            guildDAO.delete(object);
        }

        LOGGER.info("Left guild {}", event.getGuild().getName());
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor() == null || event.getAuthor().isBot()) return;
        if (!event.getGuild().isAvailable()) return;
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) return;
        if (event.getGuild().getIdLong() != 509030978484699136L) return;

        GuildObject object = guildDAO.get(event.getGuild().getIdLong()).get();

        String rawMessage = event.getMessage().getContentRaw();
        String prefix = object.getGuildSettings().getPrefix() != null ? object.getGuildSettings().getPrefix() : Config.getConfig("discord").getString("prefix");

        if (rawMessage.matches("^<@!?" + event.getJDA().getSelfUser().getId() + ">$")) {
            event.getChannel().sendMessage("The server's prefix is `" + prefix + "`.").queue();
            return;
        }

        /*
         * Anything below this point should be moved outside of the event and into the CommandManager to handle it. That
         * way we can have an expiring cache with the guild data without continuously retrieving it on every message.
         */

        List<String> arguments = new ArrayList<>(Arrays.asList(rawMessage.split("\\s+")));
        String commandName = arguments.get(0);

        if (commandName.startsWith(prefix)) {
            Command command = Shard.getSingleton().getCommandManager().findCommand(commandName.substring(prefix.length()));

            if (command == null) return;
            /*
             * 1. Check to see if user has blacklist role.
             * 2. Check to see if module (category) is disabled.
             * 3. Check to see if user has permission.
             * 4. Check to see if command requires nsfw channel
             * 5. Execute command
             */

            if (object.getGuildSettings().getRoles().getBlacklistRole() != 0L && event.getMember().getRoles().contains(event.getGuild().getRoleById(object.getGuildSettings().getRoles().getBlacklistRole()))) {
                return;
            }

            // TODO: This just looks ugly...
            if (command.getConfiguration().category() == CommandCategory.FUN && object.getGuildSettings().getModules().isFun()) return;
            if (command.getConfiguration().category() == CommandCategory.INTEGRATION && object.getGuildSettings().getModules().isIntegration()) return;
            if (command.getConfiguration().category() == CommandCategory.MISCELLANEOUS && object.getGuildSettings().getModules().isMiscellaneous()) return;
            if (command.getConfiguration().category() == CommandCategory.MODERATION && object.getGuildSettings().getModules().isModeration()) return;
            if (command.getConfiguration().category() == CommandCategory.MUSIC && object.getGuildSettings().getModules().isMusic()) return;
            if (command.getConfiguration().category() == CommandCategory.UTILITY && object.getGuildSettings().getModules().isUtility()) return;
            if (command.nsfw() && !event.getChannel().isNSFW()) {
                event.getChannel().sendMessage("This command requires the channel to be in NSFW mode.").queue();
                return;
            }

            arguments.remove(0);
            CommandArgument commandArgument = new CommandArgument(arguments);
            CommandContext commandContext = new CommandContext(event.getMessage());

            // TODO(nsylke): Need a backup way for those who haven't given TypicalBot the permission.
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
                try {
                    command.execute(commandContext, commandArgument);
                } catch (UnsupportedOperationException ex) {
                    event.getMessage().getChannel().sendMessage(ex.getMessage()).queue();
                }
            }
        }
    }
}
