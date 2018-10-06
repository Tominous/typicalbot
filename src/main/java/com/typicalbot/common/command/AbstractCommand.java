/**
 * TypicalBot - A multipurpose discord bot
 * Copyright (C) 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.typicalbot.common.command;

import com.typicalbot.common.command.annotation.Premium;
import com.typicalbot.common.command.annotation.Private;

public abstract class AbstractCommand {
    public abstract void invoke();

    /**
     * Check to see if a command is restricted to TypicalBot developers.
     *
     * @return Whether the command is private or not.
     */
    public boolean isPrivate() {
        return this.getClass().isAnnotationPresent(Private.class);
    }

    /**
     * Check to see if a command is restricted to premium users.
     *
     * @return Whether the command is premium or not.
     */
    public boolean isPremium() {
        return this.getClass().isAnnotationPresent(Premium.class);
    }
}
