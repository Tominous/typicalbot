package com.typicalbot.commands;

import com.typicalbot.command.Command;
import com.typicalbot.command.CommandListener;
import com.typicalbot.command.CommandSource;

/**
 * @since 3.0.0
 */
public class CreditsCommand implements CommandListener {
    @Command(name = "credits", aliases = {"credit"}, usage = "$credits")
    public void credit(CommandSource source, String[] args) {
    }
}
