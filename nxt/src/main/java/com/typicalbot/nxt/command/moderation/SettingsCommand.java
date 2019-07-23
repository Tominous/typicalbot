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
package com.typicalbot.nxt.command.moderation;

import com.typicalbot.command.*;
import com.typicalbot.nxt.data.mongo.dao.GuildDAO;
import com.typicalbot.nxt.data.mongo.objects.GuildObject;
import com.typicalbot.nxt.util.Pageable;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Role;

import java.util.Arrays;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = {"settings", "set"})
public class SettingsCommand implements Command {
    private String[] settings = new String[]{
        "adminrole",
        "announcements",
        "autorole",
        "autorolesilent",
        "blacklistrole",
        "djrole",
        "dmcommand",
        "goodbyemessage",
        "locale",
        "logs",
        "logs-join",
        "logs-leave",
        "logs-nickname",
        "logs-voicejoin",
        "logs-voiceleave",
        "mode",
        "modlogs",
        "modlogs-ban",
        "modlogs-kick",
        "modlogs-mute",
        "modlogs-purge",
        "modlogs-unban",
        "modlogs-unmute",
        "modrole",
        "modules-fun",
        "modules-integration",
        "modules-interaction",
        "modules-miscellaneous",
        "modules-moderation",
        "modules-music",
        "modules-utility",
        "modules-webhook",
        "music-defaultvolume",
        "music-pause",
        "music-play",
        "music-queuelimit",
        "music-resume",
        "music-skip",
        "music-stop",
        "music-timelimit",
        "music-unqueue",
        "music-volume",
        "muterole",
        "nickname",
        "prefix",
        "preservedata",
        "subscriber",
        "welcomemessage"
    };

    @Override
    public String[] usage() {
        return new String[]{
            "settings list",
            "settings view [setting]",
            "settings edit [setting] [value]"
        };
    }

    @Override
    public String description() {
        return "View or customize your servers setting and enable/disable specific features.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_ADMINISTRATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkArguments(argument);

        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();

        if (argument.get(0).equalsIgnoreCase("list")) {
            Pageable<String> opts = new Pageable<>(Arrays.asList(settings));

            if (argument.length() >= 2) {
                int page = Integer.parseInt(argument.get(1));

                if (page > opts.getMaxPages()) {
                    opts.setPage(opts.getMaxPages());
                } else {
                    opts.setPage(page);
                }
            } else {
                opts.setPage(1);
            }

            StringBuilder builder = new StringBuilder();

            builder.append("__**Available settings**__").append("\n\n");

            for (String setting : opts.getListForPage()) {
                builder.append(" • ").append(setting).append("\n");
            }

            builder.append("\n").append("Page ").append(opts.getPage()).append(" / ").append(opts.getMaxPages());

            context.sendMessage(builder.toString());
        } else if (argument.get(0).equalsIgnoreCase("view")) {
            if (argument.length() < 2) {
                context.sendMessage("Not enough arguments.");
                return;
            }

            String setting = argument.get(1);
            Object value = "";

            switch (setting) {
                case "adminrole":
                    value = object.getGuildSettings().getRoles().getAdminRole();
                    break;
                case "announcements":
                    value = object.getGuildSettings().getAnnouncementsId();
                    break;
                case "autorole":
                    value = object.getGuildSettings().getRoles().getAutorole();
                    break;
                case "autorolesilent":
                    value = object.getGuildSettings().getRoles().isAutoroleSilent();
                    break;
                case "blacklistrole":
                    value = object.getGuildSettings().getRoles().getBlacklistRole();
                    break;
                case "djrole":
                    value = object.getGuildSettings().getRoles().getDjRole();
                    break;
                case "dmcommand":
                    value = object.getGuildSettings().isDmCommand();
                    break;
                case "goodbyemessage":
                    value = object.getGuildSettings().getGoodbyeMessage();
                    break;
                case "locale":
                    value = object.getGuildSettings().getLocale();
                    break;
                case "logs":
                    value = object.getGuildSettings().getLogs().getLogsId();
                    break;
                case "logs-join":
                    value = object.getGuildSettings().getLogs().isLogJoin();
                    break;
                case "logs-leave":
                    value = object.getGuildSettings().getLogs().isLogLeave();
                    break;
                case "logs-nickname":
                    value = object.getGuildSettings().getLogs().isLogNickname();
                    break;
                case "logs-voicejoin":
                    value = object.getGuildSettings().getLogs().isLogVoiceJoin();
                    break;
                case "logs-voiceleave":
                    value = object.getGuildSettings().getLogs().isLogVoiceLeave();
                    break;
                case "mode":
                    value = object.getGuildSettings().getMode();
                    break;
                case "modlogs":
                    value = object.getGuildSettings().getLogs().getModlogsId();
                    break;
                case "modlogs-ban":
                    value = object.getGuildSettings().getLogs().isModlogBan();
                    break;
                case "modlogs-kick":
                    value = object.getGuildSettings().getLogs().isModlogKick();
                    break;
                case "modlogs-mute":
                    value = object.getGuildSettings().getLogs().isModlogMute();
                    break;
                case "modlogs-purge":
                    value = object.getGuildSettings().getLogs().isModlogPurge();
                    break;
                case "modlogs-unban":
                    value = object.getGuildSettings().getLogs().isModlogUnban();
                    break;
                case "modlogs-unmute":
                    value = object.getGuildSettings().getLogs().isModlogUnmute();
                    break;
                case "modrole":
                    value = object.getGuildSettings().getRoles().getModeratorRole();
                    break;
                case "modules-fun":
                    value = object.getGuildSettings().getModules().isFun();
                    break;
                case "modules-integration":
                    value = object.getGuildSettings().getModules().isIntegration();
                    break;
                case "modules-interaction":
                    value = object.getGuildSettings().getModules().isInteraction();
                    break;
                case "modules-miscellaneous":
                    value = object.getGuildSettings().getModules().isMiscellaneous();
                    break;
                case "modules-moderation":
                    value = object.getGuildSettings().getModules().isModeration();
                    break;
                case "modules-music":
                    value = object.getGuildSettings().getModules().isMusic();
                    break;
                case "modules-utility":
                    value = object.getGuildSettings().getModules().isUtility();
                    break;
                case "modules-webhook":
                    value = object.getGuildSettings().getModules().isWebhook();
                    break;
                case "music-defaultvolume":
                    value = object.getGuildSettings().getMusic().getDefaultVolume();
                    break;
                case "music-pause":
                    value = object.getGuildSettings().getMusic().isPause();
                    break;
                case "music-play":
                    value = object.getGuildSettings().getMusic().isPlay();
                    break;
                case "music-queuelimit":
                    value = object.getGuildSettings().getMusic().getQueueLimit();
                    break;
                case "music-resume":
                    value = object.getGuildSettings().getMusic().isResume();
                    break;
                case "music-skip":
                    value = object.getGuildSettings().getMusic().isSkip();
                    break;
                case "music-stop":
                    value = object.getGuildSettings().getMusic().isStop();
                    break;
                case "music-timelimit":
                    value = object.getGuildSettings().getMusic().getTimeLimit();
                    break;
                case "music-unqueue":
                    value = object.getGuildSettings().getMusic().isUnqueue();
                    break;
                case "music-volume":
                    value = object.getGuildSettings().getMusic().isVolume();
                    break;
                case "muterole":
                    value = object.getGuildSettings().getRoles().getMuteRole();
                    break;
                case "nickname":
                    value = object.getGuildSettings().isNickname();
                    break;
                case "prefix":
                    value = object.getGuildSettings().getPrefix();
                    break;
                case "preservedata":
                    value = object.isPreserveData();
                    break;
                case "subscriber":
                    value = object.getGuildSettings().getRoles().getSubscriberRole();
                    break;
                case "welcomemessage":
                    value = object.getGuildSettings().getWelcomeMessage();
                    break;
                default:
                    value = null;
                    break;
            }

            if (value == null) {
                context.sendMessage("That setting does not exist.");
                return;
            }

            if (value instanceof Long) {
                context.sendMessage("__**Current value**__: {0}", Long.parseLong(value.toString()));
            } else if (value instanceof Integer) {
                context.sendMessage("__**Current value**__: {0}", Integer.parseInt(value.toString()));
            } else if (value instanceof Boolean) {
                context.sendMessage("__**Current value**__: {0}", Boolean.parseBoolean(value.toString()));
            } else {
                context.sendMessage("__**Current value**__: {0}", value.toString());
            }
        } else if (argument.get(0).equalsIgnoreCase("edit")) {
            if (argument.length() < 2) {
                context.sendMessage("Not enough arguments.");
                return;
            }

            String setting = argument.get(1);
            String newValue = String.join(" ", argument.getArguments().subList(2, argument.getArguments().size()));

            switch (setting) {
                case "adminrole":
                    Role adminRole = context.getRole(newValue);

                    if (adminRole == null) {
                        context.sendMessage("The role doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getRoles().setAdminRole(adminRole.getIdLong());
                    break;
                case "announcements":
                    GuildChannel announcementsChannel = context.getChannel(newValue);

                    if (announcementsChannel == null) {
                        context.sendMessage("The channel doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().setAnnouncementsId(announcementsChannel.getIdLong());
                    break;
                case "autorole":
                    Role autoroleRole = context.getRole(newValue);

                    if (autoroleRole == null) {
                        context.sendMessage("The role doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getRoles().setAutorole(autoroleRole.getIdLong());
                    break;
                case "autorolesilent":
                    boolean bool = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getRoles().setAutoroleSilent(bool);
                    break;
                case "blacklistrole":
                    Role blacklistRole = context.getRole(newValue);

                    if (blacklistRole == null) {
                        context.sendMessage("The role doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getRoles().setBlacklistRole(blacklistRole.getIdLong());
                    break;
                case "djrole":
                    Role djRole = context.getRole(newValue);

                    if (djRole == null) {
                        context.sendMessage("The role doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getRoles().setDjRole(djRole.getIdLong());
                    break;
                case "dmcommand":
                    boolean dmCommand = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().setDmCommand(dmCommand);
                    break;
                case "goodbyemessage":
                    object.getGuildSettings().setGoodbyeMessage(newValue);
                    break;
                case "locale":
                    throw new UnsupportedOperationException("That setting hasn't been implemented yet.");
                case "logs":
                    GuildChannel logsChannel = context.getChannel(newValue);

                    if (logsChannel == null) {
                        context.sendMessage("The channel doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getLogs().setLogsId(logsChannel.getIdLong());
                    break;
                case "logs-join":
                    boolean logsJoin = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setLogJoin(logsJoin);
                    break;
                case "logs-leave":
                    boolean logsLeave = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setLogLeave(logsLeave);
                    break;
                case "logs-nickname":
                    boolean logsNickname = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setLogNickname(logsNickname);
                    break;
                case "logs-voicejoin":
                    boolean logsVoiceJoin = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setLogVoiceJoin(logsVoiceJoin);
                    break;
                case "logs-voiceleave":
                    boolean logsVoiceLeave = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setLogVoiceLeave(logsVoiceLeave);
                    break;
                case "mode":
                    throw new UnsupportedOperationException("That setting isn't implemented yet.");
                case "modlogs":
                    GuildChannel modlogsChannel = context.getChannel(newValue);

                    if (modlogsChannel == null) {
                        context.sendMessage("The channel doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getLogs().setModlogsId(modlogsChannel.getIdLong());
                    break;
                case "modlogs-ban":
                    boolean modlogsBan = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setModlogBan(modlogsBan);
                    break;
                case "modlogs-kick":
                    boolean modlogsKick = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setModlogKick(modlogsKick);
                    break;
                case "modlogs-mute":
                    boolean modlogsMute = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setModlogMute(modlogsMute);
                    break;
                case "modlogs-purge":
                    boolean modlogsPurge = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setModlogPurge(modlogsPurge);
                    break;
                case "modlogs-unban":
                    boolean modlogsUnban = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setModlogUnban(modlogsUnban);
                    break;
                case "modlogs-unmute":
                    boolean modlogsUnmute = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getLogs().setModlogUnmute(modlogsUnmute);
                    break;
                case "modrole":
                    Role modRole = context.getRole(newValue);

                    if (modRole == null) {
                        context.sendMessage("The role doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getRoles().setModeratorRole(modRole.getIdLong());
                    break;
                case "modules-fun":
                    boolean modulesFun = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getModules().setFun(modulesFun);
                    break;
                case "modules-integration":
                    boolean modulesIntegration = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getModules().setIntegration(modulesIntegration);
                    break;
                case "modules-interaction":
                    boolean modulesInteraction = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getModules().setInteraction(modulesInteraction);
                    break;
                case "modules-miscellaneous":
                    boolean modulesMiscellaneous = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getModules().setMiscellaneous(modulesMiscellaneous);
                    break;
                case "modules-moderation":
                    boolean modulesModeration = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getModules().setModeration(modulesModeration);
                    break;
                case "modules-music":
                    boolean modulesMusic = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getModules().setMusic(modulesMusic);
                    break;
                case "modules-utility":
                    boolean modulesUtility = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getModules().setUtility(modulesUtility);
                    break;
                case "modules-webhook":
                    boolean modulesWebhook = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getModules().setWebhook(modulesWebhook);
                    break;
                case "music-defaultvolume":
                    int defaultVolume = Integer.parseInt(newValue);

                    if (defaultVolume < 0 || defaultVolume > 100) {
                        context.sendMessage("Default volume must be between 0-100.");
                        return;
                    }

                    object.getGuildSettings().getMusic().setDefaultVolume(defaultVolume);
                    break;
                case "music-pause":
                    boolean musicPause = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getMusic().setPause(musicPause);
                    break;
                case "music-play":
                    boolean musicPlay = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getMusic().setPlay(musicPlay);
                    break;
                case "music-queuelimit":
                    int musicQueuelimit = Integer.parseInt(newValue);

                    object.getGuildSettings().getMusic().setQueueLimit(musicQueuelimit);
                    break;
                case "music-resume":
                    boolean musicResume = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getMusic().setResume(musicResume);
                    break;
                case "music-skip":
                    boolean musicSkip = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getMusic().setSkip(musicSkip);
                    break;
                case "music-stop":
                    boolean musicStop = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getMusic().setStop(musicStop);
                    break;
                case "music-timelimit":
                    int musicTimelimit = Integer.parseInt(newValue);

                    object.getGuildSettings().getMusic().setTimeLimit(musicTimelimit);
                    break;
                case "music-unqueue":
                    boolean musicUnqueue = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getMusic().setUnqueue(musicUnqueue);
                    break;
                case "music-volume":
                    boolean musicVolume = Boolean.parseBoolean(newValue);

                    object.getGuildSettings().getMusic().setVolume(musicVolume);
                    break;
                case "muterole":
                    Role muteRole = context.getRole(newValue);

                    if (muteRole == null) {
                        context.sendMessage("The role doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getRoles().setMuteRole(muteRole.getIdLong());
                    break;
                case "nickname":
                    boolean nickname = Boolean.parseBoolean(newValue);

                    object.setPreserveData(nickname);
                    break;
                case "prefix":
                    if (newValue.length() > 5) {
                        context.sendMessage("Prefix must be 5 or less characters long.");
                        return;
                    }

                    object.getGuildSettings().setPrefix(newValue);
                    break;
                case "preservedata":
                    boolean preserveData = Boolean.parseBoolean(newValue);

                    object.setPreserveData(preserveData);
                    break;
                case "subscriber":
                    Role subscriberRole = context.getRole(newValue);

                    if (subscriberRole == null) {
                        context.sendMessage("The role doesn't exist.");
                        return;
                    }

                    object.getGuildSettings().getRoles().setSubscriberRole(subscriberRole.getIdLong());
                    break;
                case "welcomemessage":
                    object.getGuildSettings().setWelcomeMessage(newValue);
                    break;
                default:
                    newValue = null;
                    break;
            }

            if (newValue == null) {
                context.sendMessage("That setting doesn't exist.");
                return;
            }

            dao.update(object);
            context.sendMessage("Successfully updated `{0}` to `{1}`.", setting, newValue.toString());
        } else {
            context.sendMessage("Incorrect usage.");
        }
    }
}
