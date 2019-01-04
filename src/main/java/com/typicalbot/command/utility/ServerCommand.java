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

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import net.dv8tion.jda.core.EmbedBuilder;

@CommandConfiguration(triggers = {"serverinfo", "sinfo"}, description = "Displays the server's information.", embed = true)
public class ServerCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        String header = "**__Server information for:__** " + context.getMessage().getGuild().getName() + "\n";
        String name = "Name                : " + context.getMessage().getGuild().getName() + " (" + context.getMessage().getGuild().getId() + ")\n";
        String owner = "Owner               : " + context.getMessage().getGuild().getOwner().getUser().getName() + "#" + context.getMessage().getGuild().getOwner().getUser().getDiscriminator() + "\n";
        //TODO(AKSKL): Format creation date into a more 'readable' string
        String created = "Created             : " + context.getMessage().getGuild().getCreationTime().toString() + "\n";
        String region = "Region              : " + context.getMessage().getGuild().getRegion().toString() + "\n";
        //String verification_level
        String icon = "Icon                : " + context.getMessage().getGuild().getIconUrl() + "\n";
        String numOfChannels = "Channels            : " + context.getMessage().getGuild().getChannels().size() + "\n";
        String numOfMembers = "Members             : " + context.getMessage().getGuild().getMembers().size() + "\n";
        String numOfRoles = "Roles               : " + context.getMessage().getGuild().getRoles().size() + "\n";
        String numOfEmotes = "Emotes              : " + context.getMessage().getGuild().getEmotes().size() + "\n"; //changed "emoji" to "emotes" in the rewrite
        context.sendMessage(header + "```" + name + owner + created + region + icon + numOfChannels + numOfMembers + numOfRoles + numOfEmotes + "```");
    }

    @Override
    public void embed(CommandContext context, CommandArgument argument) {
        String name = context.getMessage().getGuild().getName() + " (" + context.getMessage().getGuild().getId() + ")";
        String owner = context.getMessage().getGuild().getOwner().getUser().getName() + "#" + context.getMessage().getGuild().getOwner().getUser().getDiscriminator() + " (" + context.getMessage().getGuild().getOwner().getUser().getId() + ")";
        String created = context.getMessage().getGuild().getCreationTime().toString();
        String region = context.getMessage().getGuild().getRegion().toString();
        String numOfChannels = Integer.toString(context.getMessage().getGuild().getChannels().size());
        String numOfMembers = Integer.toString(context.getMessage().getGuild().getMembers().size());
        String numOfRoles = Integer.toString(context.getMessage().getGuild().getRoles().size());
        String numOfEmotes = Integer.toString(context.getMessage().getGuild().getEmotes().size()); //changed "emoji" to "emotes" in the rewrite
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(0x00ADFF)
                .setTitle("Server Information")
                .addField("» Name", name, false)
                .addField("» Owner", owner, false)
                .addField("» Created", created, true)
                .addField("» Region", region, true)
                .addField("» Channels", numOfChannels, true)
                .addField("» Members", numOfMembers, true)
                .addField("» Roles", numOfRoles, true)
                .addField("» Emotes", numOfEmotes, true)
                .setThumbnail(context.getMessage().getGuild().getIconUrl())
                //TODO(nsylke): make the TB icon a constant
                .setFooter("TypicalBot", "https://images-ext-2.discordapp.net/external/qYPuNcjM4PjaKvsmlc-lcHhtJ8RZ-txaxYMDQmWL0g8/https/www.typicalbot.com/x/images/icon.png");
        context.sendEmbed(embed.build());
    }
}
