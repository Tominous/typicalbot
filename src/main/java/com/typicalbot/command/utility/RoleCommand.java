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
import com.typicalbot.util.StringUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;

import java.time.format.DateTimeFormatter;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"role", "roleinfo", "rinfo"})
public class RoleCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (!argument.has()) {
            context.sendMessage("No args");
            return;
        }

        Role role = context.getMessage().getMentionedRoles().get(0);

        if (role == null) {
            context.sendMessage("Role is null");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(role.getName());
        builder.addField("ID", Long.toString(role.getIdLong()), true);
        builder.addField("Hoisted", StringUtil.firstUpperCase(Boolean.toString(role.isHoisted())), true);
        builder.addField("Mentionable", StringUtil.firstUpperCase(Boolean.toString(role.isMentionable())), true);
        builder.addField("Managed", StringUtil.firstUpperCase(Boolean.toString(role.isManaged())), true);
        builder.addField("Position", Integer.toString(role.getPosition()), true);
        builder.addField("Permissions", String.format("[Calculate](https://discordapi.com/permissions.html#%d)", role.getPermissionsRaw()), true);
        // TODO(nsylke): Check if member list is empty & limit length to 900
        builder.addField("Members", context.getMessage().getGuild().getMembers().stream().filter(member -> member.getRoles().contains(role)).map(Member::getEffectiveName).collect(Collectors.joining(", ")), false);
        builder.setColor(role.getColor());
        builder.setFooter("Created " + role.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), "https://typicalbot.com/x/images/icon.png");

        context.sendEmbed(builder.build());
    }
}
