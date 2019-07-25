/*
 * Copyright 2019 Bryan Pikaard, Nicholas Sylke and the TypicalBot contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.extension;

import com.google.common.collect.Lists;
import com.typicalbot.command.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public abstract class Extension {
    public static final Path EXTENSION_DIR = Paths.get(System.getProperty("user.dir")).resolve("extensions");

    private Path path;
    private File directory;
    private ExtensionManifest manifest;
    private ExtensionClassLoader classLoader;
    private List<Command> commands = Lists.newArrayList();

    public Extension() {
    }

    void init(Path path, ExtensionManifest manifest, ExtensionClassLoader classLoader) {
        if (this.path == null) {
            this.path = path;
            this.directory = EXTENSION_DIR.resolve(manifest.id()).toFile();
            this.manifest = manifest;
            this.classLoader = classLoader;
        }
    }

    void release() {
        try {
            if (this.classLoader != null) {
                this.classLoader.close();
                this.classLoader = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLoad() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void registerCommand(Command command) {
        this.commands.add(command);
    }

    public void registerCommands(Command... commands) {
        Collections.addAll(this.commands, commands);
    }

    public Path getPath() {
        return path;
    }

    public File getDirectory() {
        return directory;
    }

    public ExtensionManifest getManifest() {
        return manifest;
    }

    public ExtensionClassLoader getClassLoader() {
        return classLoader;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
