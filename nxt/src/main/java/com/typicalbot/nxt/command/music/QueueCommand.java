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

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.typicalbot.audio.QueuedTrack;
import com.typicalbot.nxt.audio.GuildMusicManager;
import com.typicalbot.command.*;
import com.typicalbot.nxt.util.AudioUtil;

import java.util.ArrayList;
import java.util.List;

@CommandConfiguration(category = CommandCategory.MUSIC, aliases = "queue")
public class QueueCommand implements Command {
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

        if (musicManager.scheduler.getQueue().isEmpty()) {
            context.sendMessage("Nothing is in the queue.");
            return;
        }

        AudioTrack track = musicManager.player.getPlayingTrack();
        List<QueuedTrack> scheduledTracks = new ArrayList<>(musicManager.scheduler.getQueue());
        StringBuilder builder = new StringBuilder();

        builder.append("**__Currently playing:__ ")
            .append(track.getInfo().title)
            .append("** requested by **")
            .append(context.getJDA().getUserById(track.getUserData(Long.class)).getAsTag())
            .append("**\n\n");

        scheduledTracks.subList(0, (scheduledTracks.size() >= 10 ? 10 : scheduledTracks.size())).forEach(t -> builder.append("[")
            .append(AudioUtil.format(t.getTrack().getDuration()))
            .append("] **")
            .append(t.getTrack().getInfo().title)
            .append("** requested by **")
            .append(context.getJDA().getUserById(t.getUniqueId()).getAsTag())
            .append("**\n"));

        context.sendMessage(builder.toString());
    }
}
