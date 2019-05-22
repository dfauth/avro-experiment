package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class SimpleSchemaProcessor extends SchemaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSchemaProcessor.class);

    public SimpleSchemaProcessor(SchemaHandler handler) {
        super(handler);
    }

    @Override
    public SchemaProcessor process(Optional<String> name, Schema schema) {
        // nothing to do here
        handler.onSimple(name, schema);
        return this;
    }
}
