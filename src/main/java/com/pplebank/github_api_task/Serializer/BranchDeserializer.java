package com.pplebank.github_api_task.Serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.pplebank.github_api_task.Entity.Branch;

import java.io.IOException;

public class BranchDeserializer extends StdDeserializer<Branch> {


    public BranchDeserializer() {
        this(null);
    }

    public BranchDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Branch deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);
        String name =  node.get("name").asText();
        String lastCommit = node.get("commit").get("sha").asText();

        return new Branch(name, lastCommit);
    }
}