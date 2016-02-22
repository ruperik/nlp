package com.patton.david.nlp.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer {

    public String serialize(Object obj) {
        return null;
    }

    public void serializeToFile(Object obj, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
            ObjectWriter writer = mapper.writer();
            writer.with(SerializationFeature.INDENT_OUTPUT).writeValue(os, obj);
        }
    }

    public Object deserialize(String str) {
        return null;
    }

    public Object deserializeFromFile(String filename) {
        return null;
    }
}
