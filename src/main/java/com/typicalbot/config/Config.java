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
