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
