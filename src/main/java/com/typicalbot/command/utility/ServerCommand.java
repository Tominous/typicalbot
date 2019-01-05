/**
 * Copyright 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;
import java.util.stream.Collectors;

@CommandConfiguration(triggers = {"serverinfo", "sinfo"}, description = "Displays the server's information.", embed = true)
public class ServerCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        /* VARIABLES TO RETURN IN THE FINAL MESSAGE */
        String header = "**__Server information for:__** " + context.getMessage().getGuild().getName() + "\n";
        String name = "Name                : " + context.getMessage().getGuild().getName() + " (" + context.getMessage().getGuild().getId() + ")\n";
        String owner = "Owner               : " + context.getMessage().getGuild().getOwner().getUser().getName() + "#" + context.getMessage().getGuild().getOwner().getUser().getDiscriminator() + "\n";
        //TODO(AKSKL): Format creation date into a more 'readable' string
        String created = "Created             : " + context.getMessage().getGuild().getCreationTime().toString() + "\n";
        String region = "Region              : " + context.getMessage().getGuild().getRegion().toString() + "\n";
        //TODO: Verification level?
        String icon = "Icon                : " + context.getMessage().getGuild().getIconUrl() + "\n";
        String numOfChannels = "Channels            : " + context.getMessage().getGuild().getChannels().size() + "\n";
        String numOfMembers = "Members             : " + context.getMessage().getGuild().getMembers().size() + "\n";
        String numOfRoles = "Roles               : " + context.getMessage().getGuild().getRoles().size() + "\n";
        String numOfEmotes = "Emotes              : " + context.getMessage().getGuild().getEmotes().size() + "\n"; //changed "emoji" to "emotes" in the rewrite

        /* FINAL MESSAGE (put into a code block */
        context.sendMessage(header + "```" + name + owner + created + region + icon + numOfChannels + numOfMembers + numOfRoles + numOfEmotes + "```");
    }

    @Override
    public void embed(CommandContext context, CommandArgument argument) {
        /* VARIABLES TO RETURN IN THE FINAL MESSAGE */
        String name = context.getMessage().getGuild().getName() + " (" + context.getMessage().getGuild().getId() + ")";
        String owner = context.getMessage().getGuild().getOwner().getUser().getName() + "#" + context.getMessage().getGuild().getOwner().getUser().getDiscriminator() + " (" + context.getMessage().getGuild().getOwner().getUser().getId() + ")";
        String created = context.getMessage().getGuild().getCreationTime().toString();
        String region = context.getMessage().getGuild().getRegion().toString();
        int numOfChannels = context.getMessage().getGuild().getChannels().size();
        int numOfMembers = context.getMessage().getGuild().getMembers().size();
        int numOfRoles = context.getMessage().getGuild().getRoles().size();
        int numOfEmotes = context.getMessage().getGuild().getEmotes().size(); //changed "emoji" to "emotes" in the rewrite

        /* Begin building the embed */
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(0x00ADFF)
                .setTitle("Server Information")
                .addField("» Name", name, false)
                .addField("» Owner", owner, false)
                .addField("» Created", created, true)
                .addField("» Region", region, true)
                .addField("» Channels", Integer.toString(numOfChannels), true)
                .addField("» Members", Integer.toString(numOfMembers), true);

        /* Both roles and emotes can be listed, but we must first check to see if there are any to begin with. */
        //Roles
        if (numOfRoles > 0) {
            //This converts the Role objects into a displayable list
            List<Role> rolesList = context.getMessage().getGuild().getRoles();
            String roles = rolesList.stream().map(Role::getName).collect(Collectors.joining(", "));

            //Removes the everyone ping
            roles = removeEveryoneMentions(roles);

            //Adds the roles list to the embed
            embed.addField("» Roles", roles, false);
        } else {
            //Since here there are no roles, we will just return the number of roles, which is zero (This theoretically should never happen)
            embed.addField("» Roles", Integer.toString(numOfRoles), true);
        }

        //Emotes
        if (numOfEmotes > 0) {
            //This converts the Emote objects into a displayable list
            List<Emote> emotesList = context.getMessage().getGuild().getEmotes();
            String emotes = emotesList.stream().map(Emote::getName).collect(Collectors.joining(", "));

            //Adds the emotes list to the embed
            embed.addField("» Emotes", emotes, false);
        } else {
            //Since there are no emotes, we will return the number of emotes, which is zero
            embed.addField("» Emotes", Integer.toString(numOfEmotes), true);
        }

        //Finish off the embed with the thumbnail and footer
        embed
                .setThumbnail(context.getMessage().getGuild().getIconUrl())
                //TODO(nsylke): make the TB icon a constant
                .setFooter("TypicalBot", "https://images-ext-2.discordapp.net/external/qYPuNcjM4PjaKvsmlc-lcHhtJ8RZ-txaxYMDQmWL0g8/https/www.typicalbot.com/x/images/icon.png");

        //Send the embed
        context.sendEmbed(embed.build());
    }

    public String removeEveryoneMentions(String input) {
        //\u200B is a zero-width space, which nullifies the mention
        return input.replace("@everyone", "@\u200Beveryone");
    }
}
