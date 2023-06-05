package com.pplebank.github_api_task.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pplebank.github_api_task.Serializer.BranchDeserializer;
import lombok.Data;

@Data
@JsonDeserialize(using = BranchDeserializer.class)
public class Branch {
    private String name;
    private String lastCommit;

    public Branch() {
    }

    public Branch(String name, String lastCommit) {
        this.name = name;
        this.lastCommit = lastCommit;
    }
}