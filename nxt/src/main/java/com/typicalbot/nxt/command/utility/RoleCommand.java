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
package com.typicalbot.nxt.command.utility;

import com.typicalbot.command.*;
import com.typicalbot.nxt.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"role", "roleinfo", "rinfo"})
public class RoleCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);
        CommandCheck.checkArguments(argument);

        // TODO(nsylke): Add support for Role names (no mention) and Role id
        Role role = context.getRole(argument.get(0));

        if (role == null) {
            context.sendMessage("The role specified does not exist.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(role.getName());
        builder.addField("ID", Long.toString(role.getIdLong()), true);
        builder.addField("Hoisted", StringUtil.capitalize(Boolean.toString(role.isHoisted())), true);
        builder.addField("Mentionable", StringUtil.capitalize(Boolean.toString(role.isMentionable())), true);
        builder.addField("Managed", StringUtil.capitalize(Boolean.toString(role.isManaged())), true);
        if (role.getColor() != null) {
            builder.addField("Color", String.format("%d, %d, %d", role.getColor().getRed(), role.getColor().getGreen(), role.getColor().getBlue()), true);
            builder.setColor(role.getColor());
        }
        builder.addField("Position", Integer.toString(role.getPosition()), true);
        builder.addField("Permissions", String.format("[Calculate](https://discordapi.com/permissions.html#%d)", role.getPermissionsRaw()), true);
        // TODO(nsylke): Check if member list is empty & limit length to 900
        String members = context.getMessage().getGuild().getMembers().stream().filter(member -> member.getRoles().contains(role)).map(Member::getEffectiveName).collect(Collectors.joining(", "));
        builder.addField("Members", (members.length() > 900 ? members.substring(0, 900) + " and more..." : members), false);
        builder.setFooter("Created on " + role.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), null);

        context.sendEmbed(builder.build());
    }
}
