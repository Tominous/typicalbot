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
package com.typicalbot.command.core;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;

@CommandConfiguration(category = CommandCategory.CORE, aliases = {"invite", "oauth"})
public class InviteCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "invite"
        };
    }

    @Override
    public String description() {
        return "Receive the OAuth2 authorization link for TypicalBot.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (context.getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("TypicalBot Invite");
            builder.setDescription("[Click here](" + context.getJDA().asBot().getInviteUrl(Permission.ADMINISTRATOR) + ") to invite TypicalBot to your guild.");
            builder.setColor(CommandContext.TYPICALBOT_BLUE);

            context.sendEmbed(builder.build());
        } else {
            context.sendMessage("You can invite TypicalBot using this URL: <{0}>.", context.getJDA().asBot().getInviteUrl(Permission.ADMINISTRATOR));
        }
    }
}
