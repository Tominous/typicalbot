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
