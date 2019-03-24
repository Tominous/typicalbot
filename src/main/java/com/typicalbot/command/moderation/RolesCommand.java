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
package com.typicalbot.command.moderation;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "roles")
public class RolesCommand implements Command {
    public String[] usage() {
        return new String[]{
            "roles create [name]",
            "roles delete [name]",
            "roles clone [role] [name]",
            "roles give [user] [role]",
            "roles take [user] [role]"
        };
    }

    @Override
    public String description() {
        return "Manage or view roles in a server.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_ADMINISTRATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        /*
         * roles create <name> [bits]  ->  roles create Mod 268435478 (role has manage_channels, manage_roles, kick, ban)
         *
         * roles delete <role>
         *
         * roles clone <role> <name>
         *
         * roles give <user> <role>
         * roles take <user> <role>
         *
         * roles public add <role>
         * roles public remove <role>
         * roles public list
         *
         * roles reaction add <messageId> <emote> <role>
         * roles reaction remove <messageId> <emote>
         * roles reaction list
         *
         * roles list
         *
         * roles edit <role> <prop> <value>  ->  roles edit @Mod name Moderator
         *
         * Properties:
         *  - name
         *  - color
         *  - position
         *  - hoist
         *  - mention
         *  - bits [For advanced users]
         *
         * For advanced users
         *
         * roles permissions @Mod +kick_members -ban_members
         *
         * + Allow
         * - Disallow
         */
        throw new UnsupportedOperationException("This command has not been implemented yet.");
    }
}
