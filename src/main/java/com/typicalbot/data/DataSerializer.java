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
package com.typicalbot.data;

import java.io.*;

// TODO(nsylke): Documentation
public class DataSerializer {
    // TODO(nsylke): Separate the two methods, and make interfaces.

    public void serialize(Object object, OutputStream stream) throws IOException {
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException();
        }

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }

    public Object deserialize(InputStream stream) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(stream);

        try {
            return objectInputStream.readObject();
        } catch (ClassNotFoundException ex) {
            throw new IOException();
        }
    }
}
