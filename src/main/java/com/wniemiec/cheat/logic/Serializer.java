package com.wniemiec.cheat.logic;

import java.io.*;

class Serializer<T> {

    final static String VECTORS_PATH = "./vectors";

    void serialize(T vector, File file) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(vector);
        }
    }

    @SuppressWarnings("unchecked")
    T deserialize(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            return (T) input.readObject();
        }
    }
}
