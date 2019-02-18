/*
 * Copyright 2019 Bryan Pikaard & Nicholas Sylke
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
package com.typicalbot.command;

public enum CommandPermission {
    GUILD_MEMBER(0),
    GUILD_MODERATOR(1),
    GUILD_ADMINISTRATOR(2),
    GUILD_OWNER(3),

    TYPICALBOT_MODERATOR(9),
    TYPICALBOT_ADMINISTRATOR(10);

    private final int level;

    CommandPermission(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}
