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
package com.typicalbot.nxt.command.core;

import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.util.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

@CommandConfiguration(category = CommandCategory.CORE, aliases = {"donate", "patron", "patreon"})
public class DonateCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "donate"
        };
    }

    @Override
    public String description() {
        return "Donate to the TypicalBot maintainers.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        if (context.getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("TypicalBot Donate");
            builder.setDescription("If you would like to support the TypicalBot maintainers, you can donate using one of the options below.");
            builder.setColor(Color.TYPICALBOT_BLUE.rgb());

            builder.addField("One-time Donation", "[PayPal](https://paypal.me/typicalbot)", true);
            builder.addField("Recurring Donation", "[Patreon](https://patreon.com/typicalbot)", true);
            builder.addBlankField(true);

            context.sendEmbed(builder.build());
        } else {
            context.sendMessage("If you would like to support the TypicalBot maintainers, you can donate using one of the options below.\n\nOne-time donation: <https://paypal.me/typicalbot>\nRecurring donation: <https://patreon.com/typicalbot>");
        }
    }
}
