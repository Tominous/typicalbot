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
package com.typicalbot.command.fun;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.api.entities.User;

import java.util.Random;


@CommandConfiguration(category = CommandCategory.FUN, aliases = {"shoot", "pew"})
public class ShootCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "shoot <@mention>"
        };
    }

    @Override
    public String description() {
        return "'Shoots' the mentioned user.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        User mention = context.getUser(argument.get(0));
        User author = context.getMessage().getAuthor();

        String[] options = {"Bam! Headshot.", "Yikes! Missed by a mile.", ""};
        Random rand = new Random();
        int x = rand.nextInt(options.length);



        if(mention == author){
            context.sendMessage("%s just shot at themselves! :scream: %s", author.getAsMention(), options[x]);
        } else{
            context.sendMessage("%s just shot at %s! :scream: %s", author.getAsMention(), mention.getAsMention(), options[x]);
        }
    }
}
