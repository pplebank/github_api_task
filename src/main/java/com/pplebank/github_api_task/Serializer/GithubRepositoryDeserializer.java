package com.pplebank.github_api_task.Serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.pplebank.github_api_task.Entity.GithubRepository;

import java.io.IOException;

public class GithubRepositoryDeserializer extends StdDeserializer<GithubRepository> {


    public GithubRepositoryDeserializer() {
        this(null);
    }

    public GithubRepositoryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public GithubRepository deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {


        JsonNode node = jp.getCodec().readTree(jp);
        String repoName =  node.get("name").asText();
        String ownerLogin = node.get("owner").get("login").asText();
        boolean isFork = node.get("fork").asBoolean();
        String branchesUrl = node.get("branches_url").asText().replace("{/branch}", "");

        return new GithubRepository(repoName, ownerLogin, isFork, branchesUrl);
    }
}
