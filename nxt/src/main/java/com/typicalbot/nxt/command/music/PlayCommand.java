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

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.typicalbot.nxt.audio.GuildMusicManager;
import com.typicalbot.command.*;
import com.typicalbot.nxt.shard.Shard;
import com.typicalbot.nxt.util.AudioUtil;
import com.typicalbot.nxt.util.SentryUtil;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;

@CommandConfiguration(category = CommandCategory.MUSIC, aliases = "play")
public class PlayCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkArguments(argument);

        String video;

        try {
            new URL(argument.toString());
            video = argument.toString();
        } catch (MalformedURLException ex) {
            video = "ytsearch: " + argument.toString();
        }

        final String fVideo = video;

        GuildMusicManager musicManager = AudioUtil.getGuildAudioPlayer(context.getGuild());
        Shard.getSingleton().getPlayerManager().loadItemOrdered(musicManager, video, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                context.sendMessage("Enqueued **{0}**", track.getInfo().title);
                findVoiceChannel(context);
                track.setUserData(context.getAuthor().getIdLong());
                musicManager.scheduler.queue(track, context.getAuthor().getIdLong());
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (playlist.getTracks().size() == 1 || playlist.isSearchResult()) {
                    AudioTrack track = playlist.getSelectedTrack() == null ? playlist.getTracks().get(0) : playlist.getSelectedTrack();
                    trackLoaded(track);
                } else if (playlist.getSelectedTrack() != null) {
                    trackLoaded(playlist.getSelectedTrack());
                } else {
                    context.sendMessage("Enqueued **{0}** (first track of **{1}**)", playlist.getTracks().get(0).getInfo().title, playlist.getName());
                    findVoiceChannel(context);
                    playlist.getTracks().forEach(track -> musicManager.scheduler.queue(track, context.getAuthor().getIdLong()));
                }
            }

            @Override
            public void noMatches() {
                context.sendMessage("Could not find any results for **{0}**.", fVideo);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                context.sendMessage("Looks like an unexpected error has occurred. Please contact our support team for more information.");
                SentryUtil.capture(exception, PlayCommand.class);
            }
        });
    }

    private void findVoiceChannel(CommandContext context) {
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
