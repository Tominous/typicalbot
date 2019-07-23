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

@CommandConfiguration(category = CommandCategory.MODERATION, aliases = "adcheck")
public class AdcheckCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "adcheck"
        };
    }

    @Override
    public String description() {
        return "Check if any members of a server have a server invite in their playing status.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MODERATOR;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        throw new UnsupportedOperationException("This command has not been implemented yet.");
    }
}