package com.typicalbot.command.interaction;

import com.typicalbot.command.*;
import net.dv8tion.jda.core.entities.User;

@CommandConfiguration(category = CommandCategory.INTERACTION, aliases = "poke")
public class PokeCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "poke [@user]"
        };
    }

    @Override
    public String description() {
        return "Poke another user.";
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
            context.sendMessage("You can't poke yourself.");
        } else {
            context.sendMessage(context.getAuthor().getName() + " poked " + target.getName() + "!");
        }
    }
}
