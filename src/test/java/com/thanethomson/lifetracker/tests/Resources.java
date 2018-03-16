package com.thanethomson.lifetracker.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Resources {

    private static final Logger logger = LoggerFactory.getLogger(Resources.class);

    public static String load(String name) {
        try {
            return IOUtils.resourceToString(name, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(String.format("Unable to load resource: %s", name), e);
            return null;
        }
    }

    public static JsonNode loadJson(String name, ObjectMapper mapper) {
        String content = load(name);
        try {
            return (content == null) ? null : mapper.readTree(content);
        } catch (IOException e) {
            logger.error(String.format("Unable to parse resource as JSON: %s", name), e);
            return null;
        }
    }

}
