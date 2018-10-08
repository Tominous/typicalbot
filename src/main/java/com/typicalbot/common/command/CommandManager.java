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

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CommandManager {
    private Set<BaseCommand> commands;

    public CommandManager() {
        this.commands = new HashSet<>();

        new FastClasspathScanner(BaseCommand.class.getPackage().getName()).matchClassesImplementing(BaseCommand.class, clazz -> {
            try {
                BaseCommand command = clazz.getConstructor().newInstance();
                this.register(command);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }).scan();
    }

    private void register(BaseCommand... commands) {
        Collections.addAll(this.commands, commands);
    }

    public void process() {
    }

    public Set<BaseCommand> getCommands() {
        return this.commands;
    }
}
