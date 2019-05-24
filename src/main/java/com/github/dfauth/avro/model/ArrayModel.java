package com.github.dfauth.avro.model;

import org.apache.avro.Schema;


public class ArrayModel extends Model {

    private Model elementType;

    public ArrayModel(Schema s) {
        super(s);
    }

    @Override
    public void resolve(Resolver resolver) {
        elementType = resolver.resolve(schema.getElementType());
    }
}
