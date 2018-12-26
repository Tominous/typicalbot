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

import com.typicalbot.console.ConsoleReader;
import com.typicalbot.data.DataSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TypicalBot {
    public static final Path HOME_PATH = Paths.get(System.getProperty("user.dir"));

    public TypicalBot() throws IOException {
        // TODO: Switch `System.out.println(...)` to use Logger.

        System.out.println("  _____                   _                  _   ____            _   ");
        System.out.println(" |_   _|  _   _   _ __   (_)   ___    __ _  | | | __ )    ___   | |_ ");
        System.out.println("   | |   | | | | | '_ \\  | |  / __|  / _` | | | |  _ \\   / _ \\  | __|");
        System.out.println("   | |   | |_| | | |_) | | | | (__  | (_| | | | | |_) | | (_) | | |_ ");
        System.out.println("   |_|    \\__, | | .__/  |_|  \\___|  \\__,_| |_| |____/   \\___/   \\__|");
        System.out.println("          |___/  |_|");

        System.out.println();

        System.out.println("TypicalBot created by Bryan Pikaard and Nicholas Sylke");
        System.out.println("TypicalBot is licensed under the Apache 2.0 license");

        System.out.println();

        Arrays.asList("config", "bin", "logs").forEach(directory -> {
            if (!Files.exists(HOME_PATH.resolve(directory))) {
                try {
                    Files.createDirectory(HOME_PATH.resolve(directory));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Arrays.asList("app", "database", "discord", "filter", "sentry").forEach(config -> {
            if (!Files.exists(HOME_PATH.resolve(String.format("config/%s.yml", config)))) {
                export(HOME_PATH.resolve(String.format("config/%s.yml", config)), String.format("/config/%s.yml", config));
            }
        });

        if (!Files.exists(HOME_PATH.resolve("bin/discord.dat"))) {
            ConsoleReader reader = new ConsoleReader();

            System.out.println("Please enter the token to register it with the TypicalBot software.");
            String token = reader.readLine().replaceAll("(\\r|\\n|\\t)", "");

            // TODO: Run a single shard to grab clientID and save it as `token:clientId`.

            DataSerializer serializer = new DataSerializer();
            serializer.serialize(token, new FileOutputStream(new File(HOME_PATH.resolve("bin/discord.dat").toString())));

            System.out.println("Please restart the application.");

            reader.close();
            System.exit(0);
        }
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