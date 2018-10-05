package com.typicalbot.commands.informative;

import com.typicalbot.common.command.AbstractCommand;
import com.typicalbot.common.command.annotation.CommandAlias;

@CommandAlias(value = "ping|pong")
public class PingCommand extends AbstractCommand {
    @Override
    public void invoke() {
    }
}
