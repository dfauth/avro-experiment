package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class ArraySchemaProcessor extends SchemaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ArraySchemaProcessor.class);

    public ArraySchemaProcessor(SchemaHandler handler) {
        super(handler);
    }

    @Override
    public SchemaProcessor process(Optional<String> name, Schema schema) {
        handler.startArray(name, schema);
        // nothing yet
//        logger.info("fullname: "+schema.getFullName());
        super.process(name, schema.getElementType());
        handler.endArray(name, schema);
        return this;
    }
}
