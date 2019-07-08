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
package com.typicalbot.util;

import com.google.common.collect.Maps;

import javax.annotation.concurrent.Immutable;
import java.util.Map;

@Immutable
public class Color {
    private static Map<Integer, Color> cache = Maps.newConcurrentMap();

    public static final Color TYPICALBOT_BLUE = Color.of(0x1976D2);
    public static final Color TYPICALBOT_SUCCESS = Color.of(0x00FF00);
    public static final Color TYPICALBOT_ERROR = Color.of(0xFF0000);

    private final byte red;
    private final byte green;
    private final byte blue;

    private Color(int red, int green, int blue) {
        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;
    }

    public static Color of(int red, int green, int blue) {
        int rgb = red << 16 | green << 8 | blue;

        if (!cache.containsKey(rgb)) {
            cache.put(rgb, new Color(red, green, blue));
        }

        return cache.get(rgb);
    }

    public static Color of(int rgb) {
        return of(rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb & 0xFF);
    }

    /**
     * Get the red component in the range of 0-255 of the sRGB space.
     *
     * @return the red component
     */
    public int red() {
        return 0xFF & red;
    }

    /**
     * Get the green component in the range of 0-255 of the sRGB space.
     *
     * @return the green component
     */
    public int green() {
        return 0xFF & green;
    }

    /**
     * Get the blue component in the range of 0-255 of the sRGB space.
     *
     * @return the blue component
     */
    public int blue() {
        return 0xFF & blue;
    }

    /**
     * Get the rgb value representing the color in the default sRGB model.
     *
     * @return the rgb value
     */
    public int rgb() {
        return red() << 16 | green() << 8 | blue();
    }
}
