package com.github.igocoelho;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.MediaType;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

class TestReporterDemo {

    @Test
    void reportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("a status message");
    }

    @Test
    void reportKeyValuePair(TestReporter testReporter) {
        testReporter.publishEntry("a key", "a value");
    }

    @Test
    void reportMultipleKeyValuePairs(TestReporter testReporter) {
        Map<String, String> values = new HashMap<>();
        values.put("user name", "dk38");
        values.put("award year", "1974");

        testReporter.publishEntry(values);
    }

    @Test
    void reportFiles(TestReporter testReporter, @TempDir Path tempDir) throws Exception {

        testReporter.publishFile("test1.txt", MediaType.TEXT_PLAIN_UTF_8,
            file -> Files.write(file, singletonList("Test 1")));

        Path existingFile = Files.write(tempDir.resolve("test2.txt"), singletonList("Test 2"));
        testReporter.publishFile(existingFile, MediaType.TEXT_PLAIN_UTF_8);

        testReporter.publishDirectory("test3", dir -> {
            Files.write(dir.resolve("nested1.txt"), singletonList("Nested content 1"));
            Files.write(dir.resolve("nested2.txt"), singletonList("Nested content 2"));
        });

        Path existingDir = Files.createDirectory(tempDir.resolve("test4"));
        Files.write(existingDir.resolve("nested1.txt"), singletonList("Nested content 1"));
        Files.write(existingDir.resolve("nested2.txt"), singletonList("Nested content 2"));
        testReporter.publishDirectory(existingDir);
    }
}