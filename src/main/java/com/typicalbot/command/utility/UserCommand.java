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
package com.typicalbot.command.utility;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.util.StringUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"user", "userinfo", "uinfo"})
public class UserCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        User target = context.getMessage().getAuthor();

        if (argument.has()) {
            User temp = context.getUser(argument.get(0));

            if (temp != null) {
                target = temp;
            }
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(target.getAsTag() + " Information");
        builder.addField("ID", target.getId(), true);
        builder.addField("Name", target.getName(), true);
        builder.addField("Discriminator", target.getDiscriminator(), true);
        builder.addField("Status", StringUtil.capitalize(context.getMessage().getGuild().getMember(target).getOnlineStatus().getKey()), true);
        builder.addField("Joined Discord", target.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
        builder.addField("Joined Server", context.getMessage().getGuild().getMember(target).getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);

//        List<Activity> activities = context.getMessage().getGuild().getMember(target).getActivities();
//
//        if (!activities.isEmpty()) {
//            builder.addField("Playing", activities.get(0).getName(), true);
//        }

        builder.addField("Discord Nitro", StringUtil.capitalize(Boolean.toString(target.getAvatarId().startsWith("a_"))), true);
        builder.addField("TypicalBot Prime", "False", true);

        if (!context.getMessage().getGuild().getMember(target).getRoles().isEmpty()) {
            builder.addField("Roles", context.getMessage().getGuild().getMember(target).getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")), false);
        }

        builder.setThumbnail(target.getEffectiveAvatarUrl());

        context.sendEmbed(builder.build());
    }
}
