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
package com.typicalbot.nxt.data.mongo.objects;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

@Embedded
public class GuildMusicSettingObject {
    @Property("play")
    private boolean play;

    @Property("stop")
    private boolean stop;

    @Property("skip")
    private boolean skip;

    @Property("pause")
    private boolean pause;

    @Property("resume")
    private boolean resume;

    @Property("unqueue")
    private boolean unqueue;

    @Property("volume")
    private boolean volume;

    @Property("defaultVolume")
    private int defaultVolume;

    @Property("timeLimit")
    private int timeLimit;

    @Property("queueLimit")
    private int queueLimit;

    public GuildMusicSettingObject() {
        this.defaultVolume = 50;
        this.timeLimit = 300;
        this.queueLimit = 10;
    }

    public boolean isPlay() {
        return this.play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public boolean isStop() {
        return this.stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isSkip() {
        return this.skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public boolean isPause() {
        return this.pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isResume() {
        return this.resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    public boolean isUnqueue() {
        return this.unqueue;
    }

    public void setUnqueue(boolean unqueue) {
        this.unqueue = unqueue;
    }

    public boolean isVolume() {
        return this.volume;
    }

    public void setVolume(boolean volume) {
        this.volume = volume;
    }

    public int getDefaultVolume() {
        return this.defaultVolume;
    }

    public void setDefaultVolume(int defaultVolume) {
        this.defaultVolume = defaultVolume;
    }

    public int getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getQueueLimit() {
        return this.queueLimit;
    }

    public void setQueueLimit(int queueLimit) {
        this.queueLimit = queueLimit;
    }
}
