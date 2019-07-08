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
package com.typicalbot.nxt.command.fun;

import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;

import java.util.Random;

@CommandConfiguration(category = CommandCategory.FUN, aliases = {"faces", "face", "randomface"})
public class FacesCommand implements Command {
    //thanks to https://github.com/maxogden/cool-ascii-faces for the original ascii faces array
    //TODO(AKSKL): add more faces- http://asciimoji.com/ and http://upli.st/l/list-of-all-ascii-emoticons
    private String[] faces = {
        "( .-. )",
        "( .o.)",
        "( `·´ )",
        "( ° ͜ ʖ °)",
        "( ͡° ͜ʖ ͡°)",
        "( ⚆ _ ⚆ )",
        "( ︶︿︶)",
        "( ﾟヮﾟ)"
    };

    @Override
    public String[] usage() {
        return new String[]{
            "faces",
        };
    }

    @Override
    public String description() {
        return "Returns a random face.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        context.sendMessage("```" + faces[new Random().nextInt(faces.length)] + "```");
    }
}
