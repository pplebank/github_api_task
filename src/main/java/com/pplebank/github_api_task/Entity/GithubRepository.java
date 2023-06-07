package com.pplebank.github_api_task.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubRepository {

    private String name;
    private Owner owner;
    private boolean fork;
    private String branches_url;
    private List<Branch> branches;

    @JsonProperty("owner")
    public String getOwner(){
        return owner.login();
    }
    public record Owner(String login) {
    }
}