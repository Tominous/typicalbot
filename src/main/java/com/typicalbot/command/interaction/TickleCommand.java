package com.typicalbot.command.interaction;

import com.typicalbot.command.*;
import net.dv8tion.jda.core.entities.User;

@CommandConfiguration(category = CommandCategory.INTERACTION, aliases = "tickle")
public class TickleCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "tickle",
            "tickle [@user]"
        };
    }

    @Override
    public String description() {
        return "Tickle yourself or another user.";
    }

    @Override
    public CommandPermission permission() {
        return CommandPermission.GUILD_MEMBER;
    }

    @Override
    public void execute(CommandContext context, CommandArgument argument) {
        User target = context.getMessage().getAuthor();

        if (argument.has()) {
            User temp = context.getUser(argument.get(0));

            if (temp != null) {
                target = temp;
            }
        }

        if (target == context.getAuthor()) {
            context.sendMessage("You tickled yourself.");
        } else {
            context.sendMessage(context.getAuthor().getName() + " tickled " + target.getName() + "!");
        }
    }
}
