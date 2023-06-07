package com.pplebank.github_api_task.Controller;

import com.pplebank.github_api_task.Entity.GithubRepository;
import com.pplebank.github_api_task.Service.GithubRepositoryService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class GithubRepositoryController {
    private final GithubRepositoryService repositoryService;

    public GithubRepositoryController(GithubRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(value = "/repositories/{username}", headers="Accept=application/json")
    public Flux<GithubRepository> listRepositories(@PathVariable String username) {
        return repositoryService.listRepositories(username);
    }
}