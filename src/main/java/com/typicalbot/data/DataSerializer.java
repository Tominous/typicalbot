package com.typicalbot.data;

import java.io.*;

public class DataSerializer {
    // TODO: Separate the two methods, and make interfaces.
    
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
