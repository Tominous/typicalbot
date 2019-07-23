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
package com.typicalbot.command;

public enum CommandPermission {
    GUILD_MEMBER("Guild Member", 0),
    GUILD_MODERATOR("Guild Moderator", 1),
    GUILD_ADMINISTRATOR("Guild Administrator", 2),
    GUILD_OWNER("Guild Owner", 3),

    TYPICALBOT_MODERATOR("TypicalBot Moderator", 9),
    TYPICALBOT_ADMINISTRATOR("TypicalBot Administrator", 10);

    private final String name;
    private final int level;

    CommandPermission(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }
}
