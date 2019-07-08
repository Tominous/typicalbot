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
import com.typicalbot.nxt.audio.AudioLoader;
import com.typicalbot.nxt.audio.GuildMusicManager;
import com.typicalbot.nxt.command.Command;
import com.typicalbot.nxt.command.CommandArgument;
import com.typicalbot.nxt.command.CommandCategory;
import com.typicalbot.nxt.command.CommandCheck;
import com.typicalbot.nxt.command.CommandConfiguration;
import com.typicalbot.nxt.command.CommandContext;
import com.typicalbot.nxt.command.CommandPermission;
import com.typicalbot.nxt.shard.Shard;
import com.typicalbot.nxt.util.AudioUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URL;

@CommandConfiguration(category = CommandCategory.MUSIC, aliases = "play")
public class PlayCommand implements Command {
    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    private CommandContext context;

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        CommandCheck.checkArguments(argument);

        String video;
        this.context = context;

        try {
            new URL(argument.toString());
            video = argument.toString();
        } catch (Exception ex) {
            video = "ytsearch:" + argument.toString();
        }

        GuildMusicManager musicManager = AudioUtil.getGuildAudioPlayer(context.getGuild());
        this.findVoiceChannel(context);
        Shard.getSingleton().getPlayerManager().loadItemOrdered(musicManager, video, new AudioLoader(context, musicManager, video));

//        loadAndPlay(context.getMessage().getTextChannel(), video);
    }

    private void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = AudioUtil.getGuildAudioPlayer(channel.getGuild());

        Shard.getSingleton().getPlayerManager().loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title).queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by: " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        findVoiceChannel(context);
        musicManager.scheduler.queue(track);
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
