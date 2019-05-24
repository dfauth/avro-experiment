package com.github.dfauth.avro.model;

import org.apache.avro.Schema;


public class MapModel extends Model {

    private Model valueType;

    public MapModel(Schema s) {
        super(s);
    }

    @Override
    public void resolve(Resolver resolver) {
        valueType = resolver.resolve(schema.getValueType());
    }
}
