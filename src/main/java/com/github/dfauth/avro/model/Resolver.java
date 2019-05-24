package com.github.dfauth.avro.model;

import org.apache.avro.Schema;

public interface Resolver {
    Model resolve(Schema elementType);
}
