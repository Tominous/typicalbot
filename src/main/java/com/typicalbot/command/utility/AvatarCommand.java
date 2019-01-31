/**
 * Copyright 2016-2019 Bryan Pikaard & Nicholas Sylke
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
import net.dv8tion.jda.api.entities.User;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = "avatar")
public class AvatarCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        User target;

        if (!argument.has()) {
            target = context.getMessage().getAuthor();
        } else {
            target = context.getUser(argument.get(0));
        }

        String avatarId = target.getAvatarId();
        String id = target.getId();

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Avatar for " + target.getAsTag());
        builder.setDescription("[PNG](https://cdn.discordapp.com/avatars/" + id + "/" + avatarId + ".png) [JPG](https://cdn.discordapp.com/avatars/" + id + "/" + avatarId + ".jpg) [WEBP](https://cdn.discordapp.com/avatars/" + id + "/" + avatarId + ".webp)" + (avatarId.startsWith("a_") ? " [GIF](https://cdn.discordapp.com/avatars/" + id + "/" + avatarId + ".gif)" : ""));
        builder.setImage(target.getEffectiveAvatarUrl());

        context.sendEmbed(builder.build());
    }
}
