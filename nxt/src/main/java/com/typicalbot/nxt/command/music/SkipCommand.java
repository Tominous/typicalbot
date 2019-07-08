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
import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.nxt.util.AudioUtil;
import net.dv8tion.jda.api.entities.TextChannel;

@CommandConfiguration(category = CommandCategory.MUSIC, aliases = "skip")
public class SkipCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        skipTrack(context.getMessage().getTextChannel());
    }

    private void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = AudioUtil.getGuildAudioPlayer(channel.getGuild());

        if (musicManager.player.getPlayingTrack() == null) {
            channel.sendMessage("There is nothing playing.").queue();
            return;
        }

        //musicManager.scheduler.nextTrack();

        channel.sendMessage("Skipped to next track.").queue();
    }
}
