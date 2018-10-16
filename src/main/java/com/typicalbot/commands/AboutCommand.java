package com.typicalbot.commands;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandListener;

/**
 * @since 3.0.0
 */
public class AboutCommand implements CommandListener {
    @Command(name = "about", usage = "$about")
    public void about() {
    }
}
