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
package com.typicalbot.nxt.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.util.SentryUtil;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class AudioLoader implements AudioLoadResultHandler {
    private CommandContext context;
    private GuildMusicManager manager;
    private String trackUrl;

    public AudioLoader(CommandContext context, GuildMusicManager manager, String trackUrl) {
        this.context = context;
        this.manager = manager;
        this.trackUrl = trackUrl;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        this.context.sendMessage("Adding to queue {0}", track.getInfo().title);

        findVoiceChannel(this.context);
        this.manager.scheduler.queue(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }

        this.context.sendMessage("Adding to queue {0} (first track of playlist {1})", firstTrack.getInfo().title, playlist.getName());

        findVoiceChannel(this.context);
        this.manager.scheduler.queue(firstTrack);
    }

    @Override
    public void noMatches() {
        this.context.sendMessage("Could not find any results for {0}.", trackUrl);
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        this.context.sendMessage("Looks like an unexpected error has occurred. Please contact our support team for more information.");
        SentryUtil.capture(exception, AudioLoader.class);
    }

    public static void findVoiceChannel(CommandContext context) {
        if (!context.getMember().getVoiceState().inVoiceChannel()) {
            context.sendMessage("You are not in a voice channel.");
            return;
        }

        AudioManager manager = context.getGuild().getAudioManager();

        if (manager.isConnected() || manager.isAttemptingToConnect()) {
            return;
        }

        VoiceChannel channel = context.getMember().getVoiceState().getChannel();
        manager.openAudioConnection(channel);
    }
}
