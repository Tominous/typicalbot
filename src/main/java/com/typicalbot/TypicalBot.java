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
package com.typicalbot;

import com.typicalbot.core.shard.ShardManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TypicalBot {
    private static final Path HOME_PATH = Paths.get(System.getProperty("user.dir"));

    // Definitely could clean this up more
    private final String[] TB_DIRS = new String[] { "config", "bin", "logs" };
    private final String[] TB_CFGS = new String[] { "app", "database", "discord", "sentry" };

    public TypicalBot() throws InterruptedException {
        // TODO: Switch `System.out.println(...)` to use Logger.

        System.out.println("TypicalBot by Bryan Pikaard and Nicholas Sylke - https://typicalbot.com");
        System.out.println("Initializing TypicalBot v3.0.0");

        // Setup the files
        // TODO: Definitely could clean this up more
        Stream.of(TB_DIRS).forEach(directory -> {
            System.out.println("Checking for directory: " + directory);
            try {
                if (!Files.exists(HOME_PATH.resolve(directory))) {
                    System.out.println("The directory `" + directory + "` does not exist.");
                    System.out.println("Creating `" + directory + "` directory.");
                    Files.createDirectory(HOME_PATH.resolve(directory));
                }
            } catch (IOException ignored) {
            }
        });

        // TODO: Definitely could clean this up more
        Stream.of(TB_CFGS).forEach(file -> {
            System.out.println("Checking for config: " + file + ".yml");
            if (!Files.exists(HOME_PATH.resolve(String.format("config/%s.yml", file)))) {
                System.out.println("The config `" + file + ".yml` does not exist.");
                System.out.println("Creating `" + file + ".yml` config.");
                export(HOME_PATH.resolve(String.format("config/%s.yml", file)), String.format("/config/%s.yml", file));
            }
        });

        System.out.println("Launching TypicalBot v3.0.0");

        ShardManager.register(1);
    }

    public void export(Path dest, String resource) {
        InputStream stream = TypicalBot.class.getResourceAsStream(resource);
        try {
            Files.copy(stream, dest);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        try {
            new TypicalBot();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}