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
package com.typicalbot.nxt.command.music;

import com.typicalbot.nxt.audio.GuildMusicManager;
import com.typicalbot.nxt.command.*;
import com.typicalbot.nxt.util.AudioUtil;

@CommandConfiguration(category = CommandCategory.MUSIC, aliases = "stop")
public class StopCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        GuildMusicManager musicManager = AudioUtil.getGuildAudioPlayer(context.getGuild());

        if (musicManager.player.getPlayingTrack() == null) {
            context.sendMessage("Nothing is currently playing.");
            return;
        }

        musicManager.player.destroy();
        musicManager.scheduler.getQueue().clear();
        context.getGuild().getAudioManager().closeAudioConnection();
        context.sendMessage("Stopping all audio.");
    }
}
