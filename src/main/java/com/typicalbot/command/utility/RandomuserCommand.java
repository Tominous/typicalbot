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
import net.dv8tion.jda.core.entities.Member;

import java.time.format.DateTimeFormatter;
import java.util.Random;

@CommandConfiguration(triggers = {"randomuser", "randuser", "randommember", "randmember", "rando"}, description = "Gets a random user in the server. `--no_bots` prevents bots from being chosen.", embed = true)
public class RandomuserCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        Member randomMember = genRandMember(context, argument);
        context.sendMessage("Your random pick is: **" + randomMember.getUser().getAsTag() + "** (" + randomMember.getUser().getId() + ").");
    }

    @Override
    public void embed(CommandContext context, CommandArgument argument) {
        Member randomMember = genRandMember(context, argument);

        EmbedBuilder embed = new EmbedBuilder();
        embed
                .setTitle("Your random pick is:")
                .addField("» Tag", randomMember.getUser().getAsTag(), false)
                .addField("» ID", randomMember.getUser().getId(), false)
                .setThumbnail(randomMember.getUser().getAvatarUrl())
                .setFooter("Created " + context.getMessage().getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), "https://typicalbot.com/x/images/icon.png")
                .setColor(0x00ADFF);
        context.sendEmbed(embed.build());
    }

    public Member genRandMember(CommandContext context, CommandArgument argument) {
        Random randGen = new Random();
        int random = randGen.nextInt(context.getMessage().getGuild().getMembers().size());
        Member randomMember = context.getMessage().getGuild().getMembers().get(random);

        if(argument.has() && argument.get(0).equals("--no_bots")) {
            if(randomMember.getUser().isBot()) randomMember = genRandMember(context, argument);
        }

        return randomMember;
    }
}
