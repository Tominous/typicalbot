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
package com.typicalbot.config;

import com.typicalbot.util.FileUtil;
import org.simpleyaml.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static Map<String, YamlConfiguration> configs;

    public static void init() {
        configs = new HashMap<>();

        configs.put("app", YamlConfiguration.loadConfiguration(new File(FileUtil.HOME_PATH.resolve("config/app.yml").toString())));
        configs.put("database", YamlConfiguration.loadConfiguration(new File(FileUtil.HOME_PATH.resolve("config/database.yml").toString())));
        configs.put("discord", YamlConfiguration.loadConfiguration(new File(FileUtil.HOME_PATH.resolve("config/discord.yml").toString())));
        configs.put("filter", YamlConfiguration.loadConfiguration(new File(FileUtil.HOME_PATH.resolve("config/filter.yml").toString())));
        configs.put("sentry", YamlConfiguration.loadConfiguration(new File(FileUtil.HOME_PATH.resolve("config/sentry.yml").toString())));
    }

    public static YamlConfiguration getConfig(String name) {
        return configs.get(name);
    }
}
