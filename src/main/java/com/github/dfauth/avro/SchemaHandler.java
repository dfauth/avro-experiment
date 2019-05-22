package com.github.dfauth.avro;

import org.apache.avro.Schema;

import java.util.Optional;

public interface SchemaHandler {

    void startNested(Optional<String> name, Schema schema);

    void endNested(Optional<String> name, Schema schema);

    void onSimple(Optional<String> name, Schema schema);

    void startArray(Optional<String> name, Schema schema);

    void endArray(Optional<String> name, Schema schema);

    void startUnion(Optional<String> name, Schema schema);

    void endUnion(Optional<String> name, Schema schema);
}
