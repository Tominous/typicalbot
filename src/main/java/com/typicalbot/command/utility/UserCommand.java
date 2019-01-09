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
package com.typicalbot.command.utility;

import com.typicalbot.command.CommandPermission;
import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"user", "userinfo", "uinfo"})
public class UserCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        Member mentionedMember = getMember(context.getMessage());
        User mentionedUser = mentionedMember.getUser();

        /* VARIABLES TO RETURN IN THE FINAL MESSAGE */
        String header = "User information for: " + mentionedUser.getName();
        String tag = mentionedUser.getName() + "#" + mentionedUser.getDiscriminator();
        String id = mentionedUser.getId();
        String status = mentionedMember.getOnlineStatus().getKey();
        String joinDate = mentionedUser.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String serverJoinDate = mentionedMember.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        int numOfRoles = mentionedMember.getRoles().size();
        /* Begin building the embed */
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(header)
                .addField("» Tag", tag, true)
                .addField("» ID", id, true)
                .addField("» Joined Discord", joinDate, false)
                .addField("» Joined Server", serverJoinDate, false)
                //TODO(AKSKL): Make status prettier
                .addField("» Status", status, true);

        // Both a member's presence and their nickname may not exist, so we have to check first
        if (mentionedMember.getActivities().isEmpty()) {
            String presence = mentionedMember.getActivities().get(0).getName();
            embed.addField("» Playing", presence, true);
        }
        if (mentionedMember.getNickname() != null) {
            String nickname = mentionedMember.getNickname();
            embed.addField("» Nickname", nickname, true);
        }

        //Since a member may not have any roles, we must check that they have some
        if (numOfRoles > 0) {
            List<Role> rolesList = mentionedMember.getRoles();
            String roles = rolesList.stream().map(Role::getName).collect(Collectors.joining(", "));
            embed.addField("» Roles", roles, true);
        }

        //Finish off the embed with the color, thumbnail, and footer
        embed.setThumbnail(mentionedUser.getAvatarUrl())
                .setColor(0x00ADFF)
                //TODO(nsylke): make the TB icon a constant
                .setFooter("Created " + context.getMessage().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), "https://typicalbot.com/x/images/icon.png");

        //Send the embed
        context.sendEmbed(embed.build());
    }

    //A simple function to determine the target of the command
    private Member getMember(Message message) {
        if (message.getMentionedMembers().isEmpty()) {
            //if the user didn't mention anyone, the target is themself
            return message.getMember();
        } else {
            //if the user mentioned other users, the target is assumed to be the first member mentioned
            return message.getMentionedMembers().get(0);
        }
    }
}
