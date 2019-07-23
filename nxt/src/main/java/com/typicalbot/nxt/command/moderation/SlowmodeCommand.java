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
package com.typicalbot.nxt.command.moderation;

import com.typicalbot.command.*;
import com.typicalbot.nxt.util.SentryUtil;
import net.dv8tion.jda.api.Permission;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "slowmode")
public class SlowmodeCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkPermission(context.getMember(), Permission.MANAGE_CHANNEL);
        CommandCheck.checkPermission(context.getSelfMember(), Permission.MANAGE_CHANNEL);
        CommandCheck.checkArguments(argument);

        int seconds = 0;
        try {
            seconds = Integer.parseInt(argument.get(0));

            if (seconds < 0 || seconds > 120) {
                context.sendMessage("Slowmode must be in between 0 and 120 seconds.");
                return;
            }
        } catch (NumberFormatException ex) {
            SentryUtil.capture(ex, SlowmodeCommand.class);
        }

        context.getMessage().getTextChannel().getManager().setSlowmode(seconds).queue(o -> context.sendMessage("Successfully changed slowmode for {0} to {1}.", context.getMessage().getTextChannel().getAsMention(), context.getMessage().getTextChannel().getSlowmode()));
    }
}
