package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class UnionSchemaProcessor extends SchemaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(UnionSchemaProcessor.class);

    public UnionSchemaProcessor(SchemaHandler handler) {
        super(handler);
    }

    @Override
    public SchemaProcessor process(Optional<String> name, Schema schema) {
        handler.startUnion(name, schema);
        // nothing yet
//        logger.info("fullname: "+schema.getFullName());
        schema.getTypes().stream().forEach(s -> {
            super.process(name, s);
        });
        handler.endUnion(name, schema);
        return this;
    }
}
