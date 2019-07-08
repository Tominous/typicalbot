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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ColorTest {
    @Test
    void testRed() {
        Assertions.assertEquals(Color.TYPICALBOT_BLUE.red(), 25);
    }

    @Test
    void testGreen() {
        Assertions.assertEquals(Color.TYPICALBOT_BLUE.green(), 118);
    }

    @Test
    void testBlue() {
        Assertions.assertEquals(Color.TYPICALBOT_BLUE.blue(), 210);
    }

    @Test
    void testRgb() {
        Assertions.assertEquals(Color.TYPICALBOT_BLUE.rgb(), 0x1976D2);
    }
}
