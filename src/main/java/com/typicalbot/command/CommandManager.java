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
package com.typicalbot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CommandManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);

    private Set<Command> commands = new HashSet<>();

    public Set<Command> getCommands() {
        return this.commands;
    }

    public void registerCommands(Command... commands) {
        Collections.addAll(this.commands, commands);
        LOGGER.debug("Registered {} commands.", this.commands.size());
    }

    public Command findCommand(String trigger) {
        return this.commands.stream().filter(command -> Arrays.asList(command.getConfiguration().triggers()).contains(trigger)).findFirst().orElse(null);
    }
}
