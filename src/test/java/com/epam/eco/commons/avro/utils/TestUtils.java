package com.epam.eco.commons.avro.utils;

import org.apache.avro.Schema;

import java.io.IOException;
import java.io.InputStream;

public class TestUtils {

    public static Schema getScheme(String filePath) throws IOException {
        InputStream inputStream = TestUtils.class
                .getClassLoader()
                .getResourceAsStream(filePath);
        return new Schema.Parser().parse(inputStream);
    }
}
