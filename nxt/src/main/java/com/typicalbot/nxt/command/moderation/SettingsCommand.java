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

import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandCheck;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.nxt.data.mongo.dao.GuildDAO;
import com.typicalbot.nxt.data.mongo.objects.GuildObject;
import com.typicalbot.nxt.util.Pageable;

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

    private String[] guildSettings = new String[]{
        "announcements",
        "dmcommand",
        "goodbyemessage",
        "locale",
        "mode",
        "nickname",
        "prefix",
        "preservedata",
        "welcomemessage"
    };

    private String[] logSettings = new String[]{
        "logs",
        "logs-join",
        "logs-leave",
        "logs-nickname",
        "logs-voicejoin",
        "logs-voiceleave",
        "modlogs",
        "modlogs-ban",
        "modlogs-kick",
        "modlogs-mute",
        "modlogs-purge",
        "modlogs-unban",
        "modlogs-unmute"
    };

    private String[] moduleSettings = new String[]{
        "modules-fun",
        "modules-integration",
        "modules-interaction",
        "modules-miscellaneous",
        "modules-moderation",
        "modules-music",
        "modules-utility",
        "modules-webhook"
    };

    private String[] musicSettings = new String[]{
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
    };

    private String[] roleSettings = new String[]{
        "adminrole",
        "autorole",
        "autorolesilent",
        "blacklistrole",
        "djrole",
        "modrole",
        "muterole",
        "subscriber"
    };

    @Override
    public String[] usage() {
        return new String[]{
            "settings list",
            "settings view [setting]",
            "settings edit [setting]"
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

        String command = argument.get(0);

        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();

        // TODO(nsylke): Clean this up? Another days project...
        if (command.equalsIgnoreCase("list")) {
            Pageable<String> list = new Pageable<>(Arrays.asList(settings));

            if (argument.length() > 1) {
                if (isInt(argument.get(1))) {
                    list.setPage(Integer.parseInt(argument.get(1)));
                } else {
                    if (argument.get(1).equalsIgnoreCase("guild")) {
                        StringBuilder builder = new StringBuilder();

                        builder.append("__**Launcher Guild Settings**__").append("\n");

                        for (String s : guildSettings) {
                            builder.append(" • ").append(s).append("\n");
                        }

                        context.sendMessage(builder.toString());
                    } else if (argument.get(1).equalsIgnoreCase("logs")) {
                        StringBuilder builder = new StringBuilder();

                        builder.append("__**Launcher Log Settings**__").append("\n");

                        for (String s : logSettings) {
                            builder.append(" • ").append(s).append("\n");
                        }

                        context.sendMessage(builder.toString());
                    } else if (argument.get(1).equalsIgnoreCase("modules")) {
                        StringBuilder builder = new StringBuilder();

                        builder.append("__**Launcher Module Settings**__").append("\n");

                        for (String s : moduleSettings) {
                            builder.append(" • ").append(s).append("\n");
                        }

                        context.sendMessage(builder.toString());
                    } else if (argument.get(1).equalsIgnoreCase("music")) {
                        StringBuilder builder = new StringBuilder();

                        builder.append("__**Launcher Music Settings**__").append("\n");

                        for (String s : musicSettings) {
                            builder.append(" • ").append(s).append("\n");
                        }

                        context.sendMessage(builder.toString());
                    } else if (argument.get(1).equalsIgnoreCase("roles")) {
                        StringBuilder builder = new StringBuilder();

                        builder.append("__**Launcher Role Settings**__").append("\n");

                        for (String s : roleSettings) {
                            builder.append(" • ").append(s).append("\n");
                        }

                        context.sendMessage(builder.toString());
                    } else {
                        context.sendMessage("Incorrect usage. Please use `$help settings` to check usage.");
                    }

                    return;
                }
            } else {
                list.setPage(0);
            }

            StringBuilder builder = new StringBuilder();

            builder.append("__**Launcher Settings**__").append("\n\n");
            builder.append("**Page ").append(list.getPage()).append(" / ").append(list.getMaxPages()).append("**").append("\n");

            for (String str : list.getListForPage()) {
                builder.append(" • ").append(str).append("\n");
            }

            context.sendMessage(builder.toString().trim());
        } else if (command.equalsIgnoreCase("view")) {
            if (argument.get(1).equalsIgnoreCase("adminrole")) {
                context.sendMessage("{0}, the current value of `adminrole` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getRoles().getAdminRole()));
            } else if (argument.get(1).equalsIgnoreCase("announcements")) {
                context.sendMessage("{0}, the current value of `announcements` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getAnnouncementsId()));
            } else if (argument.get(1).equalsIgnoreCase("autorole")) {
                context.sendMessage("{0}, the current value of `autorole` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getRoles().getAutorole()));
            } else if (argument.get(1).equalsIgnoreCase("autorolesilent")) {
                context.sendMessage("{0}, the current value of `autorolesilent` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getRoles().isAutoroleSilent()));
            } else if (argument.get(1).equalsIgnoreCase("blacklistrole")) {
                context.sendMessage("{0}, the current value of `blacklistrole` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getRoles().getBlacklistRole()));
            } else if (argument.get(1).equalsIgnoreCase("djrole")) {
                context.sendMessage("{0}, the current value of `djrole` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getRoles().getDjRole()));
            } else if (argument.get(1).equalsIgnoreCase("dmcommand")) {
                context.sendMessage("{0}, the current value of `dmcommand` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().isDmCommand()));
            } else if (argument.get(1).equalsIgnoreCase("goodbyemessage")) {
                context.sendMessage("{0}, the current value of `goodbyemessage` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getWelcomeMessage()));
            } else if (argument.get(1).equalsIgnoreCase("locale")) {
                context.sendMessage("{0}, the current value of `locale` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLocale()));
            } else if (argument.get(1).equalsIgnoreCase("logs")) {
                context.sendMessage("{0}, the current value of `logs` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().getLogsId()));
            } else if (argument.get(1).equalsIgnoreCase("logs-join")) {
                context.sendMessage("{0}, the current value of `logs-join` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isLogJoin()));
            } else if (argument.get(1).equalsIgnoreCase("logs-leave")) {
                context.sendMessage("{0}, the current value of `logs-leave` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isLogLeave()));
            } else if (argument.get(1).equalsIgnoreCase("logs-nickname")) {
                context.sendMessage("{0}, the current value of `logs-nickname` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isLogNickname()));
            } else if (argument.get(1).equalsIgnoreCase("logs-voicejoin")) {
                context.sendMessage("{0}, the current value of `logs-voicejoin` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isLogVoiceJoin()));
            } else if (argument.get(1).equalsIgnoreCase("logs-voiceleave")) {
                context.sendMessage("{0}, the current value of `logs-voiceleave` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isLogVoiceLeave()));
            } else if (argument.get(1).equalsIgnoreCase("mode")) {
                context.sendMessage("{0}, the current value of `mode` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getMode()));
            } else if (argument.get(1).equalsIgnoreCase("modlogs")) {
                context.sendMessage("{0}, the current value of `modlogs` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().getModlogsId()));
            } else if (argument.get(1).equalsIgnoreCase("modlogs-ban")) {
                context.sendMessage("{0}, the current value of `modlogs-ban` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isModlogBan()));
            } else if (argument.get(1).equalsIgnoreCase("modlogs-kick")) {
                context.sendMessage("{0}, the current value of `modlogs-kick` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isModlogKick()));
            } else if (argument.get(1).equalsIgnoreCase("modlogs-mute")) {
                context.sendMessage("{0}, the current value of `modlogs-mute` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isModlogMute()));
            } else if (argument.get(1).equalsIgnoreCase("modlogs-purge")) {
                context.sendMessage("{0}, the current value of `modlogs-purge` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isModlogPurge()));
            } else if (argument.get(1).equalsIgnoreCase("modlogs-unban")) {
                context.sendMessage("{0}, the current value of `modlogs-unban` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isModlogUnban()));
            } else if (argument.get(1).equalsIgnoreCase("modlogs-unmute")) {
                context.sendMessage("{0}, the current value of `modlogs-unmute` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getLogs().isModlogUnmute()));
            } else if (argument.get(1).equalsIgnoreCase("modrole")) {
                context.sendMessage("{0}, the current value of `modrole` is `{1}`.", context.getMember().getAsMention(), String.valueOf(object.getGuildSettings().getRoles().getModeratorRole()));
            }
        } else if (command.equalsIgnoreCase("edit")) {

        } else {
            context.sendMessage("Incorrect usage. Please use `$help settings` to check usage.");
        }

//        switch (command) {
//            case "list":
//                context.sendMessage("Available settings:\n\n - adminrole\n - modrole");
//                return;
//            case "view":
//                switch (argument.get(1)) {
//                    case "adminrole":
//                        context.sendMessage("The current value for `adminrole` is `{0}`.", String.valueOf(object.getGuildSettings().getRoles().getAdminRole()));
//                        return;
//                    case "modrole":
//                        context.sendMessage("The current value for `modrole` is `{0}`.", String.valueOf(object.getGuildSettings().getRoles().getModeratorRole()));
//                        return;
//                    default:
//                        context.sendMessage("Couldn't find that setting.");
//                        return;
//                }
//            case "edit":
//                switch (argument.get(1)) {
//                    case "adminrole":
//                        Role adminrole = context.getRole(argument.get(2));
//
//                        if (adminrole == null) {
//                            context.sendMessage("The role specified couldn't be found.");
//                            return;
//                        }
//
//                        object.getGuildSettings().getRoles().setAdminRole(adminrole.getIdLong());
//                        dao.update(object);
//                        context.sendMessage("Successfully updated to `adminrole` to `{0}`.", String.valueOf(adminrole.getIdLong()));
//                        return;
//                    case "modrole":
//                        Role modrole = context.getRole(argument.get(2));
//
//                        if (modrole == null) {
//                            context.sendMessage("The role specified couldn't be found.");
//                            return;
//                        }
//
//                        object.getGuildSettings().getRoles().setModeratorRole(modrole.getIdLong());
//                        dao.update(object);
//                        context.sendMessage("Successfully updated to `modrole` to `{0}`.", String.valueOf(modrole.getIdLong()));
//                        return;
//                    default:
//                        context.sendMessage("Couldn't find that setting.");
//                        return;
//                }
//            default:
//                context.sendMessage("Incorrect usage. Please check `$help settings` for usage.");
//        }
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
