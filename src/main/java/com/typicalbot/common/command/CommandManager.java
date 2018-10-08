/**
 * TypicalBot - A multipurpose discord bot
 * Copyright (C) 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.typicalbot.common.command;

import com.typicalbot.commands.informative.PingCommand;
import com.typicalbot.common.Logger;
import com.typicalbot.common.command.annotation.Command;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private Logger logger = Logger.get("TypicalBot");

    private Map<String, BaseCommand> commands;

    public CommandManager() {
        this.commands = new HashMap<>();

        //        new FastClasspathScanner(BaseCommand.class.getPackage().getName()).matchClassesImplementing(BaseCommand.class, clazz -> {
        //            try {
        //                BaseCommand command = clazz.getConstructor().newInstance();
        //                this.register(command);
        //            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        //                logger.warn("Unable to register commands.");
        //            }
        //        }).scan();
        this.register(new PingCommand());
    }

    private void register(BaseCommand... commands) {
        // This can be can be cleaned...
        for (BaseCommand command : commands) {
            if (command.getClass().isAnnotationPresent(Command.class)) {
                String[] triggers = command.getClass().getAnnotation(Command.class).triggers().split("\\|");

                Arrays.stream(triggers).forEach(trigger -> this.commands.put(trigger, command));
            }
        }
    }

    public void process(Message message, Member author, TextChannel channel, Guild guild) {
        String[] parts = message.getContentRaw().split("\\s");

        // Make sure to switch to use the configuration prefix.
        if (parts[0].startsWith("v$")) {
            // Use prefix length.
            String command = parts[0].substring(2);

            if (this.commands.containsKey(command)) {
                // Removes the command from the array. Should probably switch
                // over to using an List<String> instead of String[].
                this.commands.get(command).invoke(Arrays.copyOfRange(parts, 1, parts.length), author, channel, guild);
            }
        }
    }

    public Map<String, BaseCommand> getCommands() {
        return this.commands;
    }
}
