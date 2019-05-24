package com.github.dfauth.avro.model;

import org.apache.avro.Schema;


public class RecordModel extends Model {

    public RecordModel(Schema s) {
        super(s);
    }

    @Override
    public void resolve(Resolver resolver) {
        schema.getFields().stream().forEach(f -> {
            Model model = resolver.resolve(f.schema());
            addField(f.name(), model);
        });
    }

    private void addField(String name, Model model) {

    }
}
