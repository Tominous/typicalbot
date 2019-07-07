package com.typicalbot.command.interaction;

import com.typicalbot.command.*;
import net.dv8tion.jda.api.entities.User;

@CommandConfiguration(category = CommandCategory.INTERACTION, aliases = "kiss")
public class KissCommand implements Command {
    @Override
    public String[] usage() {
        return new String[]{
            "kiss [@user]"
        };
    }

    @Override
    public String description() {
        return "Kiss another person.";
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
            context.sendMessage("You can't kiss yourself.");
        } else {
            context.sendMessage(context.getAuthor().getName() + " kissed " + target.getName() + "!");
        }
    }
}
