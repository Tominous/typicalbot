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
package com.typicalbot.command.fun;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CommandConfiguration(category = CommandCategory.FUN, aliases = "emoji")
public class EmojiCommand implements Command {
    // TODO(nsylke): Improve this and also move this to the configuration.
    private Pattern regex = Pattern.compile("(<)(:)((?:[a-z][a-z]+))(:)(\\d+)(>)");

    @Override
    public String[] usage() {
        return new String[]{
            "emoji :thonk:"
        };
    }

    @Override
    public String description() {
        return "Send an image of the emoji.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);
        CommandCheck.checkArguments(argument);

        Matcher matcher = regex.matcher(argument.get(0));
        if (matcher.find()) {
            String[] parts = argument.get(0).split(":");
            String emoji = parts[parts.length - 1].replace(">", "");

            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("Emoji for " + matcher.group(3));
            builder.setImage("https://cdn.discordapp.com/emojis/" + emoji + ".png");

            context.sendEmbed(builder.build());
        } else {
            context.sendMessage("Couldn't resolve emoji.");
        }
    }
}
