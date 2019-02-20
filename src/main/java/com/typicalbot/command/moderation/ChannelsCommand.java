/*
 * Copyright 2019 Bryan Pikaard & Nicholas Sylke
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
package com.typicalbot.command.moderation;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "channels")
public class ChannelsCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_ADMINISTRATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        /*
         * channels create text <name>
         * channels create voice <name>
         * channels create category <name>
         *
         * channels create -t <name>
         * channels create -v <name>
         * channels create -c <name>
         *
         * channels delete <channel>
         *
         * channels clone <channel> <name>
         *
         * channels edit <channel> <prop> <value>  ->  channels edit #general name #lounge
         *
         * Properties:
         *  - name
         *  - topic
         *  - slowmode (or use $slowmode)
         *  - nsfw
         *  - bitrate
         *  - userlimit
         *
         *
         * For advanced users
         *
         * channels permissions #general everyone +read_messages -send_messages
         * channels permissions #general sync
         *
         * + Allow
         * / Default
         * - Disallow
         *
         * sync Sync permissions with category
         */
        throw new UnsupportedOperationException("This command has not been implemented yet.");
    }
}
