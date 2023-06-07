package com.pplebank.github_api_task.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    private Commit commit;
    private String name;
    @JsonProperty("commit")
    public String getCommit(){
        return commit.sha();
    }
    public record Commit(String sha) {

    }
}