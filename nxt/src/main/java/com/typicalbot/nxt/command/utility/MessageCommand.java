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
package com.typicalbot.nxt.command.utility;

import com.typicalbot.command.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.time.format.DateTimeFormatter;

@CommandConfiguration(category = CommandCategory.UTILITY, aliases = {"message", "msg", "getmsg", "getmessage", "quotemessage", "quotemsg"})
public class MessageCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        EmbedBuilder builder = new EmbedBuilder();

        try {
            Message message = context.getMessage().getTextChannel().retrieveMessageById(argument.get(0)).complete();

            builder.setTitle("Quoting " + message.getAuthor().getAsTag() + ": ");
            builder.setFooter(message.getAuthor().getAsTag() + " | Sent on " + message.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), message.getAuthor().getAvatarUrl());

            //TODO(AKSKL): format emotes properly
            builder.setDescription(message.getContentDisplay());

            context.sendEmbed(builder.build());
        } catch (ErrorResponseException errorResponse) {
            if (errorResponse.getErrorCode() == 10008) {
                context.sendMessage("Error: Either an invalid message or the message isn't from this channel (#" + context.getChannel().getName() + ").");
            }
            //TODO(AKSKL): resolve other errors
        }
    }
}