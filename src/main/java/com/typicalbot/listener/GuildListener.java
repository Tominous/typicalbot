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
package com.typicalbot.listener;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.config.Config;
import com.typicalbot.data.mongo.dao.GuildDAO;
import com.typicalbot.data.mongo.objects.GuildObject;
import com.typicalbot.shard.Shard;
import com.typicalbot.util.StringUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        GuildObject object = guildDAO.get(event.getGuild().getIdLong()).get();
        if (!object.isPreserveData()) {
            guildDAO.delete(object);
        }

        LOGGER.info("Left guild {}", event.getGuild().getName());
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        GuildObject object = guildDAO.get(event.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getLogs().getLogsId() != 0L && object.getGuildSettings().getLogs().isLogJoin()) {
            TextChannel channel = event.getGuild().getTextChannelById(object.getGuildSettings().getLogs().getLogsId());

            if (channel != null) {
                // TODO: Create a class to automatically create logs and allow for embeds also.
                channel.sendMessage(String.format("**%s** has joined.", event.getUser().getAsTag())).queue();
            }
        }
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        GuildObject object = guildDAO.get(event.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getLogs().getLogsId() != 0L && object.getGuildSettings().getLogs().isLogLeave()) {
            TextChannel channel = event.getGuild().getTextChannelById(object.getGuildSettings().getLogs().getLogsId());

            if (channel != null) {
                // TODO: Create a class to automatically create logs and allow for embeds also.
                channel.sendMessage(String.format("**%s** has left.**", event.getUser().getAsTag())).queue();
            }
        }
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        GuildObject object = guildDAO.get(event.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getLogs().getLogsId() != 0L && object.getGuildSettings().getLogs().isLogVoiceJoin()) {
            TextChannel channel = event.getGuild().getTextChannelById(object.getGuildSettings().getLogs().getLogsId());

            if (channel != null) {
                // TODO: Create a class to automatically create logs and allow for embeds also.
                channel.sendMessage(String.format("**%s** has joined the **%s** voice channel.", event.getMember().getUser().getAsTag(), event.getChannelJoined().getName())).queue();
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        GuildObject object = guildDAO.get(event.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getLogs().getLogsId() != 0L && object.getGuildSettings().getLogs().isLogVoiceLeave()) {
            TextChannel channel = event.getGuild().getTextChannelById(object.getGuildSettings().getLogs().getLogsId());

            if (channel != null) {
                // TODO: Create a class to automatically create logs and allow for embeds also.
                channel.sendMessage(String.format("**%s** has left the **%s** voice channel.", event.getMember().getUser().getAsTag(), event.getChannelLeft().getName())).queue();
            }
        }
    }


    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
        GuildObject object = guildDAO.get(event.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getLogs().getLogsId() != 0L && object.getGuildSettings().getLogs().isLogNickname()) {
            TextChannel channel = event.getGuild().getTextChannelById(object.getGuildSettings().getLogs().getLogsId());

            if (channel != null) {
                // TODO: Create a class to automatically create logs and allow for embeds also.
                channel.sendMessage(String.format("**%s** has changed their nickname from **%s** to **%s**.", event.getUser().getAsTag(), event.getOldNickname(), event.getNewNickname())).queue();
            }
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor() == null || event.getAuthor().isBot()) return;
        if (!event.getGuild().isAvailable()) return;
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) return;

        GuildObject object;

        if (guildDAO.get(event.getGuild().getIdLong()).isPresent()) {
            object = guildDAO.get(event.getGuild().getIdLong()).get();
        } else {
            object = guildDAO.create(event.getGuild());
            guildDAO.update(object);
        }

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

            if (command == null) {
                if (object.getGuildSettings().isCommandSimilarity()) {
                    for (Command cmd : Shard.getSingleton().getCommandManager().getCommands()) {
                        if (StringUtil.similarity(cmd.getConfiguration().aliases()[0], commandName.substring(prefix.length())) >= 0.66) {
                            event.getChannel().sendMessage("Command not found, did you mean: " + cmd.getConfiguration().aliases()[0] + "?").queue();
                        }
                    }
                }

                return;
            }

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
            if (command.getConfiguration().category() == CommandCategory.FUN && object.getGuildSettings().getModules().isFun())
                return;
            if (command.getConfiguration().category() == CommandCategory.INTEGRATION && object.getGuildSettings().getModules().isIntegration())
                return;
            if (command.getConfiguration().category() == CommandCategory.MISCELLANEOUS && object.getGuildSettings().getModules().isMiscellaneous())
                return;
            if (command.getConfiguration().category() == CommandCategory.MODERATION && object.getGuildSettings().getModules().isModeration())
                return;
            if (command.getConfiguration().category() == CommandCategory.MUSIC && object.getGuildSettings().getModules().isMusic())
                return;
            if (command.getConfiguration().category() == CommandCategory.UTILITY && object.getGuildSettings().getModules().isUtility())
                return;

            // TODO: This just looks ugly too...
            if (command.permission().getLevel() >= 1 && command.permission().getLevel() <= 4 && (event.getAuthor().getIdLong() != event.getGuild().getOwnerIdLong())) {
                if (command.permission().equals(CommandPermission.GUILD_MODERATOR) && object.getGuildSettings().getRoles().getModeratorRole() == 0L)
                    return;
                if (command.permission().equals(CommandPermission.GUILD_ADMINISTRATOR) && object.getGuildSettings().getRoles().getAdminRole() == 0L)
                    return;

                Role modrole = event.getGuild().getRoleById(object.getGuildSettings().getRoles().getModeratorRole());
                Role adminrole = event.getGuild().getRoleById(object.getGuildSettings().getRoles().getAdminRole());

                if (command.permission().getLevel() >= 1 && (!event.getMember().getRoles().contains(modrole) || !event.getMember().getRoles().contains(adminrole))) {
                    event.getMessage().getChannel().sendMessage("You do not have permission to use this command. The required permission is " + StringUtil.capitalize(command.permission().name().replace('_', ' ')) + ".").queue();
                    return;
                }
                if (command.permission().getLevel() >= 2 && (!event.getMember().getRoles().contains(adminrole))) {
                    event.getMessage().getChannel().sendMessage("You do not have permission to use this command. The required permission is " + StringUtil.capitalize(command.permission().name().replace('_', ' ')) + ".").queue();
                    return;
                }
            }

//            if (command.permission().getLevel() >= 8 && (event.getAuthor().getIdLong() != 187342661060001792L || event.getAuthor().getIdLong() != 105408136285818880L)) {
//                event.getMessage().getChannel().sendMessage("You do not have permission to use this command.").queue();
//                return;
//            }

            if (command.nsfw() && !event.getChannel().isNSFW()) {
                event.getChannel().sendMessage("This command requires the channel to be in NSFW mode.").queue();
                return;
            }

            arguments.remove(0);
            CommandArgument commandArgument = new CommandArgument(arguments);
            CommandContext commandContext = new CommandContext(event.getMessage());

            try {
                command.execute(commandContext, commandArgument);
            } catch (UnsupportedOperationException | IllegalArgumentException | InsufficientPermissionException ex) {
                event.getMessage().getChannel().sendMessage(ex.getMessage()).queue();
            }
        }
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(event.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getRoles().getReactionRoles() == null) return;

        for (String raw : object.getGuildSettings().getRoles().getReactionRoles()) {
            String[] parts = raw.split("\\|");

            String messageId = parts[0];
            String emoteId = parts[1];
            String roleId = parts[2];

            if (event.getMessageId().equals(messageId)) {
                if (event.getReactionEmote().getId().equals(emoteId)) {
                    Role role = event.getGuild().getRoleById(roleId);

                    if (role != null) {
                        event.getGuild().addRoleToMember(event.getMember(), role).queue();
                    }
                }
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(event.getGuild().getIdLong()).get();

        if (object.getGuildSettings().getRoles().getReactionRoles() == null) return;

        for (String raw : object.getGuildSettings().getRoles().getReactionRoles()) {
            String[] parts = raw.split("\\|");

            String messageId = parts[0];
            String emoteId = parts[1];
            String roleId = parts[2];

            if (event.getMessageId().equals(messageId)) {
                if (event.getReactionEmote().getId().equals(emoteId)) {
                    Role role = event.getGuild().getRoleById(roleId);

                    if (role != null) {
                        event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
                    }
                }
            }
        }
    }
}
