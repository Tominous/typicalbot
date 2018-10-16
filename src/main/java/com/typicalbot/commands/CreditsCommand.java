package com.typicalbot.commands;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandListener;

/**
 * @since 3.0.0
 */
public class CreditsCommand implements CommandListener {
    @Command(name = "credits", aliases = {"credit"}, usage = "$credits")
    public void credit() {
    }
}
