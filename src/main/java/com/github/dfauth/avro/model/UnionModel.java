package com.github.dfauth.avro.model;

import org.apache.avro.Schema;

import java.util.ArrayList;
import java.util.List;


public class UnionModel extends Model {

    private List<Model> unionTypes = new ArrayList<>();

    public UnionModel(Schema s) {
        super(s);
    }

    @Override
    public void resolve(Resolver resolver) {
        schema.getTypes().stream().forEach(s -> {
            unionTypes.add(resolver.resolve(s));
        });
    }
}
