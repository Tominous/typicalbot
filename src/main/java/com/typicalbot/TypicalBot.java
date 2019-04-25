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
package com.typicalbot;

import com.typicalbot.config.Config;
import com.typicalbot.data.mongo.MongoManager;
import com.typicalbot.data.serialization.Deserializer;
import com.typicalbot.data.serialization.Serializer;
import com.typicalbot.data.storage.DataStructure;
import com.typicalbot.shard.ShardManager;
import com.typicalbot.util.FileUtil;
import com.typicalbot.util.SentryUtil;
import com.typicalbot.util.console.ConsoleReader;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class TypicalBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(TypicalBot.class);

    private static MongoManager mongoManager;

    public static final String VERSION = "@version@";

    public TypicalBot() throws IOException, InterruptedException, LoginException {
        LOGGER.info("  _____                   _                  _   ____            _   ");
        LOGGER.info(" |_   _|  _   _   _ __   (_)   ___    __ _  | | | __ )    ___   | |_ ");
        LOGGER.info("   | |   | | | | | '_ \\  | |  / __|  / _` | | | |  _ \\   / _ \\  | __|");
        LOGGER.info("   | |   | |_| | | |_) | | | | (__  | (_| | | | | |_) | | (_) | | |_ ");
        LOGGER.info("   |_|    \\__, | | .__/  |_|  \\___|  \\__,_| |_| |____/   \\___/   \\__|");
        LOGGER.info("          |___/  |_|");

        LOGGER.info("");

        LOGGER.info("TypicalBot created by Bryan Pikaard and Nicholas Sylke.");
        LOGGER.info("Copyright (c) 2019 Bryan Pikaard, Nicholas Sylke, and contributors.");
        LOGGER.info("TypicalBot is licensed under the Apache License, Version 2.0 (\"License\").");
        LOGGER.info("You may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0.");

        LOGGER.info("");

        Arrays.asList("config", "bin", "logs").forEach(directory -> {
            if (!Files.exists(FileUtil.HOME_PATH.resolve(directory))) {
                LOGGER.debug("Directory '{}' does not exist, creating...", directory);
                try {
                    Files.createDirectory(FileUtil.HOME_PATH.resolve(directory));
                } catch (IOException e) {
                    LOGGER.error("Unable to create directory, exiting...", e);
                    System.exit(0);
                }
            }
        });

        Arrays.asList("app", "database", "discord", "filter").forEach(file -> {
            if (!Files.exists(FileUtil.HOME_PATH.resolve("config/" + file + ".yml"))) {
                LOGGER.debug("File '{}' does not exist, creating...", file);
                FileUtil.copy(FileUtil.HOME_PATH.resolve("config/" + file + ".yml"), "/config/" + file + ".yml");
            }
        });

        if (!Files.exists(FileUtil.HOME_PATH.resolve("bin/discord.dat"))) {
            ConsoleReader reader = new ConsoleReader();

            LOGGER.info("Please enter the token to register it with the TypicalBot software.");
            String token = reader.readLine().replaceAll("(\\r|\\n|\\t)", "");

            LOGGER.info("Please wait while we retrieve the client identifier.");

            // Temp solution
            JDA jda = new JDABuilder(AccountType.BOT).setToken(token).build();
            jda.awaitReady();

            String clientId = jda.getSelfUser().getId();
            jda.shutdown();

            if (clientId == null) {
                LOGGER.error("The token entered is invalid, please restart the application.");
                System.exit(-1);
            }

            LOGGER.info("Found '{}' as the client identifier.", clientId);

            Serializer serializer = new Serializer();
            serializer.serialize(String.format("%s:%s", token, clientId), new FileOutputStream(new File(FileUtil.HOME_PATH.resolve("bin/discord.dat").toString())));

            LOGGER.info("Please restart the application.");

            reader.close();
            System.exit(0);
        }

        Deserializer deserializer = new Deserializer();
        // TODO(nsylke): Should move this outside of constructor.
        DataStructure data = new DataStructure();

        LOGGER.info("Starting TypicalBot {}", VERSION);
        /*
          Inside of the data structure should be two values. The first value, or known as '0', should be the
          token of the Discord bot; and the second value, or known as '1', should be the client identifier of
          the Discord bot.
         */
        Arrays.asList(deserializer.deserialize(new FileInputStream(new File(FileUtil.HOME_PATH.resolve("bin/discord.dat").toString()))).toString().split(":")).forEach(data::insert);
        Config.init();
        mongoManager = new MongoManager();

        Object shards = Config.getConfig("discord").get("shards");

        if (shards instanceof String && shards.equals("auto")) {
            ShardManager.register(String.valueOf(data.read(0)), String.valueOf(data.read(1)), ShardManager.getRecommendedShards(String.valueOf(data.read(0))));
        } else {
            ShardManager.register(String.valueOf(data.read(0)), String.valueOf(data.read(1)), (int) shards);
        }
    }

    public static void main(String[] args) {
        try {
            new TypicalBot();
        } catch (IOException | LoginException | InterruptedException e) {
            SentryUtil.capture(e, TypicalBot.class);
        }
    }

    public static MongoManager getMongoManager() {
        return mongoManager;
    }
}
