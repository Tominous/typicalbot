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

import com.google.common.collect.Queues;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.typicalbot.audio.QueuedTrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<QueuedTrack> queue;

    private boolean repeating;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = Queues.newLinkedBlockingQueue();
    }

    public void queue(AudioTrack track, long id) {
        if (player.getPlayingTrack() != null) {
            queue.offer(new QueuedTrack(track, id));
        } else {
            player.playTrack(track);
        }
    }

    public void next() {
        player.startTrack(queue.poll().getTrack(), false);
    }

    public void repeat(boolean repeat) {
        this.repeating = repeat;
    }

    public boolean isRepeating() {
        return this.repeating;
    }

    public void shuffle() {
        List<QueuedTrack> scheduledTracks = new ArrayList<>(queue);
        Collections.shuffle(scheduledTracks);
        queue.clear();
        queue.addAll(scheduledTracks);
    }

    public BlockingQueue<QueuedTrack> getQueue() {
        return queue;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (!endReason.mayStartNext) {
            return;
        }

        if (!repeating) {
            next();
        }

        AudioTrack clone = track.makeClone();
        clone.setUserData(track.getUserData());
        player.playTrack(clone);
    }
}
