/**
 * Copyright 2016-2019 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.util;

/**
 * @author TypicalBot
 * @since 3.0.0-alpha
 */
public class AudioUtil {
    // Prevent instantiation.
    private AudioUtil() {
    }

    public static String format(long duration) {
        if (duration == Long.MAX_VALUE) {
            return "live";
        }

        long hours = duration / 3600000L % 24;
        long minutes = duration / 60000L % 60;
        long seconds = duration / 1000L % 60;

        StringBuilder builder = new StringBuilder();

        if (hours > 0) {
            builder.append(hours < 10 ? "0" + hours : hours).append(":");
        }

        builder.append(minutes < 10 ? "0" + minutes : minutes).append(":");
        builder.append(seconds < 10 ? "0" + seconds : seconds);

        return builder.toString();
    }
}
