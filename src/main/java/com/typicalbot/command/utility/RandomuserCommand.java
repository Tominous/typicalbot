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
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Random;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"randomuser", "randuser", "randommember", "randmember", "rando"})
public class RandomuserCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        Member randomMember = genRandMember(context, argument);
        context.sendMessage("Your random pick is: **" + randomMember.getUser().getAsTag() + "** (" + randomMember.getUser().getId() + ").");
    }

    public Member genRandMember(CommandContext context, CommandArgument argument) {
        int random = new Random().nextInt(context.getMessage().getGuild().getMembers().size());
        Member randomMember = context.getMessage().getGuild().getMembers().get(random);

        if (argument.has() && argument.get(0).equals("-nobots") && randomMember.getUser().isBot()) {
            randomMember = genRandMember(context, argument);
        }

        return randomMember;
    }
}
