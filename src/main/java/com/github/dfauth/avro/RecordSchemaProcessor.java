package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class RecordSchemaProcessor extends SchemaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RecordSchemaProcessor.class);

    public RecordSchemaProcessor(SchemaHandler handler) {
        super(handler);
    }

    public SchemaProcessor process(Optional<String> name, Schema schema) {
        handler.startNested(name, schema);
        schema.getFields().forEach(f -> {
            super.process(Optional.of(f.name()), f.schema());
        });
        handler.endNested(name, schema);
        return this;
    }
}
