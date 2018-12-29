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
import com.typicalbot.data.DataStructure;
import com.typicalbot.data.DataStructureInterface;
import com.typicalbot.shard.Shard;
import com.typicalbot.shard.ShardManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TypicalBot {
    public static final Path HOME_PATH = Paths.get(System.getProperty("user.dir"));

    public TypicalBot() throws IOException, InterruptedException {
        // TODO(nsylke): Switch `System.out.println(...)` to use Logger.

        System.out.println("  _____                   _                  _   ____            _   ");
        System.out.println(" |_   _|  _   _   _ __   (_)   ___    __ _  | | | __ )    ___   | |_ ");
        System.out.println("   | |   | | | | | '_ \\  | |  / __|  / _` | | | |  _ \\   / _ \\  | __|");
        System.out.println("   | |   | |_| | | |_) | | | | (__  | (_| | | | | |_) | | (_) | | |_ ");
        System.out.println("   |_|    \\__, | | .__/  |_|  \\___|  \\__,_| |_| |____/   \\___/   \\__|");
        System.out.println("          |___/  |_|");

        System.out.println();

        System.out.println("TypicalBot created by Bryan Pikaard and Nicholas Sylke.");
        System.out.println("TypicalBot is licensed under the Apache 2.0 license.");

        System.out.println();

        // TODO(nsylke): Add debug messages
        Arrays.asList("config", "bin", "logs").forEach(directory -> {
            if (!Files.exists(HOME_PATH.resolve(directory))) {
                try {
                    Files.createDirectory(HOME_PATH.resolve(directory));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // TODO(nsylke): Add debug messages
        Arrays.asList("app", "database", "discord", "filter", "sentry").forEach(file -> {
            if (!Files.exists(HOME_PATH.resolve("config/" + file + ".yml"))) {
                export(HOME_PATH.resolve("config/" + file + ".yml"), "/config/" + file + ".yml");
            }
        });

        if (!Files.exists(HOME_PATH.resolve("bin/discord.dat"))) {
            ConsoleReader reader = new ConsoleReader();

            System.out.println("Please enter the token to register it with the TypicalBot software.");
            String token = reader.readLine().replaceAll("(\\r|\\n|\\t)", "");

            System.out.println("Please wait while we retrieve the client identifier.");

            Shard shard = new Shard(token);
            Thread.sleep(5000); // TODO(nsylke): Find the lowest millisecond we need to wait. 
            String clientId = shard.getClientId();
            shard.shutdown();

            if (clientId == null) {
                System.out.println("The token entered is invalid, please restart the application.");
                System.exit(-1);
            }

            System.out.println("Found '" + clientId + "' as the client identifier.");

            DataSerializer serializer = new DataSerializer();
            serializer.serialize(String.format("%s:%s", token, clientId), new FileOutputStream(new File(HOME_PATH.resolve("bin/discord.dat").toString())));

            System.out.println("Please restart the application.");

            reader.close();
            System.exit(0);
        }

        DataSerializer serializer = new DataSerializer();
        DataStructureInterface data = new DataStructure();

        System.out.println("Starting TypicalBot v3.0.0");
        /*
          Inside of the data structure should be two values. The first value, or known as '0', should be the
          token of the Discord bot; and the second value, or known as '1', should be the client identifier of
          the Discord bot.
         */
        Arrays.asList(serializer.deserialize(new FileInputStream(new File(HOME_PATH.resolve("bin/discord.dat").toString()))).toString().split(":")).forEach(data::insert);

        // TODO(nsylke): Start ShardManager - pass the token and client id, along with the shard count.
        ShardManager.register(String.valueOf(data.read(0)), String.valueOf(data.read(0)), 1);
    }

    private void export(Path dest, String resource) {
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
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}