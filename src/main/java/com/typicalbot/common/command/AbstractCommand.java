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
