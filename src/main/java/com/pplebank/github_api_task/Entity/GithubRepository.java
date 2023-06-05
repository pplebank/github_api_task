package com.pplebank.github_api_task.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pplebank.github_api_task.Serializer.GithubRepositoryDeserializer;
import lombok.Data;

import java.util.List;

@Data
@JsonDeserialize(using = GithubRepositoryDeserializer.class)
public class GithubRepository {
    private String name;
    private String owner;
    private boolean fork;
    private String branchesUrl;
    private List<Branch> branches;


    public GithubRepository() {
    }

    public GithubRepository(String name, String owner, boolean fork, String branchesUrl) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
        this.branchesUrl = branchesUrl;
    }

}