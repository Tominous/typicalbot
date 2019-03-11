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
package com.typicalbot.data.serialization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class Deserializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Deserializer.class);

    public Object deserialize(InputStream stream) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(stream);

        try {
            return objectInputStream.readObject();
        } catch (ClassNotFoundException ex) {
            LOGGER.debug("Failed to deserialize object type.", ex);
            throw new IOException("Failed to deserialize object type.", ex);
        }
    }
}
