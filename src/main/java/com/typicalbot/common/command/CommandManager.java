/**
 * Copyright 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
