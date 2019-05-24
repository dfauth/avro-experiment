package com.github.dfauth.avro;

import org.apache.avro.Schema;

public interface TypeCallback<T> {

    static <T> T typeOf(Schema s, TypeCallback<T> callback) {
        switch (s.getType()) {
            case RECORD:
                return callback.recordType(s);
            case MAP:
                return callback.mapType(s);
            case ARRAY:
                return callback.arrayType(s);
            case UNION:
                return callback.unionType(s);
            case ENUM:
            case FIXED:
                return callback.enumType(s);
            case STRING: case BYTES:
            case INT: case LONG:
            case FLOAT: case DOUBLE:
            case BOOLEAN: case NULL:
                return callback.simpleType(s);
            default: throw new RuntimeException("Unknown type: "+s);
        }
    }

    T recordType(Schema s);

    T mapType(Schema s);

    T arrayType(Schema s);

    T unionType(Schema s);

    T enumType(Schema s);

    T simpleType(Schema s);
}
