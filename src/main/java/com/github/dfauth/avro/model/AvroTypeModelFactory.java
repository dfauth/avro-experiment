package com.github.dfauth.avro.model;

import com.github.dfauth.avro.AvroTypeCallback;
import org.apache.avro.Schema;


public class AvroTypeModelFactory implements AvroTypeCallback<Model> {

    public static Model typeOf(Schema s) {
        return AvroTypeCallback.typeOf(s, new AvroTypeModelFactory());
    }

    @Override
    public Model recordType(Schema s) {
        return new RecordModel(s);
    }

    @Override
    public Model mapType(Schema s) {
        return new MapModel(s);
    }

    @Override
    public Model arrayType(Schema s) {
        return new ArrayModel(s);
    }

    @Override
    public Model unionType(Schema s) {
        return new UnionModel(s);
    }

    @Override
    public Model enumType(Schema s) {
        return new EnumModel(s);
    }

    @Override
    public Model simpleType(Schema s) {
        return new PrimitiveModel(s);
    }
}
