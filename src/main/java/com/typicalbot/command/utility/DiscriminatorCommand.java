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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"discriminator", "discrim"})
public class DiscriminatorCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        String discrim;

        if (argument.has()) {
            String temp = argument.get(0);

            if (temp.length() != 4) {
                context.sendMessage("Not a valid discriminator.");
                return;
            }

            discrim = temp;
        } else {
            discrim = context.getMessage().getAuthor().getDiscriminator();
        }

        List<User> users = new ArrayList<>();
        context.getGuild().getMembers().stream().filter(m -> m.getUser().getDiscriminator().equals(discrim)).forEach(m -> users.add(m.getUser()));

        if (users.isEmpty()) {
            context.sendMessage("There are no users with that discriminator.");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Users with the discriminator of " + discrim);
        builder.setColor(CommandContext.TYPICALBOT_SUCCESS);

        users.forEach(u -> {
            builder.addField(u.getAsTag(), u.getId(), true);
        });

        context.sendEmbed(builder.build());
    }
}
