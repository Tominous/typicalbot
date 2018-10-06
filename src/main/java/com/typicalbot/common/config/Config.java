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
package com.typicalbot.common.config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    /**
     * The properties file.
     */
    private final Properties file;

    /**
     * The location of the properties file.
     */
    private final String path;

    /**
     * Default constructor.
     *
     * @param path The location of the properties file to load.
     * @throws IOException
     * @throws FileNotFoundException
     */
    public Config(String path) throws IOException {
        this.file = new Properties();

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);

        if (stream != null) {
            this.file.load(stream);
            this.path = path;
            stream.close();
        } else {
            throw new FileNotFoundException("Property file '" + path + "' could not be found.");
        }
    }

    /**
     * Get a value from the configuration file.
     * @param key The config key.
     * @return The value of the config key.
     */
    public String get(String key) {
        return get(key, null);
    }

    /**
     * Get a value from the configuration file, if it doesn't exist, then use default
     * value supplied.
     *
     * @param key The config key.
     * @param defaultValue The default value for config key.
     * @return The value of the config key.
     */
    public String get(String key, String defaultValue) {
        return this.file.getProperty(key, defaultValue);
    }

    /**
     * Set a temporarily key and value in current session, must save after setting
     * to make key and value permanent.
     *
     * @param key The config key.
     * @param value The config value.
     */
    public void set(String key, String value) {
        this.file.setProperty(key, value);
    }

    /**
     * Save new key and values to the configuration file.
     *
     * @throws IOException
     */
    public void save() throws IOException {
        this.file.store(new FileOutputStream(this.path), null);
    }
}
