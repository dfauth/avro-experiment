package com.github.dfauth.avro;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.github.dfauth.avro.TypeCallback.typeOf;


public class SchemaProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SchemaProcessor.class);
    protected final SchemaHandler handler;

    public SchemaProcessor(SchemaHandler schemaHandler) {
        this.handler = schemaHandler;
    }

    public SchemaProcessor process(Schema schema) {
        return process(Optional.empty(), schema);
    }

    public SchemaProcessor process(Optional<String> name, Schema schema) {
        return typeOf(schema, new TypeCallback<SchemaProcessor>(){
            @Override
            public SchemaProcessor recordType(Schema s) {
                return new RecordSchemaProcessor(handler);
            }

            @Override
            public SchemaProcessor mapType(Schema s) {
                return new MapSchemaProcessor(handler);
            }

            @Override
            public SchemaProcessor arrayType(Schema s) {
                return new ArraySchemaProcessor(handler);
            }

            @Override
            public SchemaProcessor unionType(Schema s) {
                return new UnionSchemaProcessor(handler);
            }

            @Override
            public SchemaProcessor fixedType(Schema s) {
                return new SimpleSchemaProcessor(handler);
            }

            @Override
            public SchemaProcessor simpleType(Schema s) {
                return new SimpleSchemaProcessor(handler);
            }
        }).process(name, schema);
    }
}
