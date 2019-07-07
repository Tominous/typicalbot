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
package com.typicalbot.command.music;

import com.typicalbot.audio.GuildMusicManager;
import com.typicalbot.command.Command;
import com.typicalbot.command.CommandArgument;
import com.typicalbot.command.CommandCategory;
import com.typicalbot.command.CommandCheck;
import com.typicalbot.command.CommandConfiguration;
import com.typicalbot.command.CommandContext;
import com.typicalbot.command.CommandPermission;
import com.typicalbot.util.AudioUtil;
import net.dv8tion.jda.api.Permission;

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
            context.sendMessage("There is nothing playing.");
            return;
        }

        CommandCheck.checkPermission(context.getSelfMember(), Permission.MESSAGE_EMBED_LINKS);

        StringBuilder builder = new StringBuilder();
        builder.append("Now Playing: ")
            .append(musicManager.player.getPlayingTrack().getInfo().title)
            .append("\n\n")
            .append("Queue:")
            .append("\n");

        musicManager.scheduler.getQueue().forEach(element -> builder.append("[").append(AudioUtil.format(element.getDuration())).append("] ").append(element.getInfo().title).append("\n"));

        context.sendMessage(builder.toString());
    }
}
