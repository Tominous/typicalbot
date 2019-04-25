package com.typicalbot.command.interaction;

import com.typicalbot.command.*;
import net.dv8tion.jda.core.entities.User;

@CommandConfiguration(category = CommandCategory.INTERACTION, aliases = "pat")
public class PatCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "pat [@user]"
        };
    }

    @Override
    public String description() {
        return "Pat another user.";
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
            context.sendMessage("You can't pat yourself.");
        } else {
            context.sendMessage(context.getAuthor().getName() + " patted " + target.getName() + " on the back!");
        }
    }
}
