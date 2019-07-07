package com.typicalbot.command.interaction;

import com.typicalbot.command.*;
import net.dv8tion.jda.api.entities.User;

@CommandConfiguration(category = CommandCategory.INTERACTION, aliases = "bite")
public class BiteCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "bite",
            "bite [@user]"
        };
    }

    @Override
    public String description() {
        return "Bite yourself or another person.";
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
            context.sendMessage(target.getName() + " just bit themselves.");
        } else {
            context.sendMessage(context.getAuthor().getName() + " just bit " + target.getName() + "!");
        }
    }
}
