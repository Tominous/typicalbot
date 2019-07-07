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
import com.typicalbot.data.mongo.dao.GuildDAO;
import com.typicalbot.data.mongo.objects.GuildObject;
import com.typicalbot.data.mongo.objects.GuildRoleSettingObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = "level")
public class LevelCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        User target = context.getAuthor();

        if (argument.has()) {
            User temp = context.getUser(argument.get(0));

            if (temp != null) {
                target = temp;
            }
        }

        GuildDAO dao = new GuildDAO();
        GuildObject object = dao.get(context.getGuild().getIdLong()).get();
        GuildRoleSettingObject settings = object.getGuildSettings().getRoles();

        Role adminrole = context.getRole(String.valueOf(settings.getAdminRole()));
        Role modrole = context.getRole(String.valueOf(settings.getModeratorRole()));

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(target.getAsTag() + "'s Permission Level");
        builder.setColor(CommandContext.TYPICALBOT_BLUE);

        if (target.getIdLong() == 187342661060001792L || target.getIdLong() == 105408136285818880L) {
            // Nick, HyperCoder2975
            builder.setDescription(getLevel(CommandPermission.TYPICALBOT_ADMINISTRATOR));
        } else if (target.getIdLong() == 193382030812250112L || target.getIdLong() == 214045916246573057L || target.getIdLong() == 234558143051464704L || target.getIdLong() == 242773887610388480L || target.getIdLong() == 218459651098935297L || target.getIdLong() == 187980183569956864L || target.getIdLong() == 156859037890117632L) {
            // AKSKL, TheTechnicalFox, Morphoxeris, steve, Tobias, Xenotater, Packer
            builder.setDescription(getLevel(CommandPermission.TYPICALBOT_MODERATOR));
        } else if (target.getIdLong() == context.getGuild().getOwnerIdLong()) {
            builder.setDescription(getLevel(CommandPermission.GUILD_OWNER));
        } else if (adminrole != null && context.getGuild().getMember(target).getRoles().contains(adminrole)) {
            builder.setDescription(getLevel(CommandPermission.GUILD_ADMINISTRATOR));
        } else if (modrole != null && context.getGuild().getMember(target).getRoles().contains(modrole)) {
            builder.setDescription(getLevel(CommandPermission.GUILD_MODERATOR));
        } else {
            builder.setDescription(getLevel(CommandPermission.GUILD_MEMBER));
        }

        context.sendEmbed(builder.build());
    }

    private String getLevel(CommandPermission permission) {
        return String.format("Level %d | %s", permission.getLevel(), permission.getName());
    }
}
