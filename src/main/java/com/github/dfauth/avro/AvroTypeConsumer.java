package com.github.dfauth.avro;

import org.apache.avro.Schema;

public interface AvroTypeConsumer {

    static void typeOf(Schema s, AvroTypeConsumer consumer) {
        switch (s.getType()) {
            case RECORD:
                consumer.acceptRecordSchema(s);
                break;
            case MAP:
                consumer.acceptMapSchema(s);
                break;
            case ARRAY:
                consumer.acceptTypeSchema(s);
                break;
            case UNION:
                consumer.acceptUnionSchema(s);
                break;
            case ENUM:
            case FIXED:
                consumer.acceptFixedSchema(s);
                break;
            case STRING: case BYTES:
            case INT: case LONG:
            case FLOAT: case DOUBLE:
            case BOOLEAN: case NULL:
                consumer.acceptSimpleSchema(s);
                break;
            default: throw new RuntimeException("Unknown type: "+s);
        }
    }

    default void acceptRecordSchema(Schema s){}

    default void acceptMapSchema(Schema s){}

    default void acceptTypeSchema(Schema s){}

    default void acceptUnionSchema(Schema s){}

    default void acceptFixedSchema(Schema s){}

    default void acceptSimpleSchema(Schema s){}
}
