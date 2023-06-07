package com.pplebank.github_api_task.Service;

import com.pplebank.github_api_task.Entity.Branch;
import com.pplebank.github_api_task.Entity.GithubRepository;
import com.pplebank.github_api_task.Exception.NotFoundException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class GithubRepositoryService {
    private final WebClient webClient;


    public GithubRepositoryService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<GithubRepository> listRepositories(String username) {
        var url = "https://api.github.com/users/" + username + "/repos";
        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, this::handleErrorResponse)
                .bodyToFlux(GithubRepository.class)
                .flatMap(this::fetchBranchesForRepository);
    }

    private Mono<GithubRepository> fetchBranchesForRepository(GithubRepository repo) {
        return webClient.get()
                .uri(repo.getBranches_url().replace("{/branch}", ""))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleErrorResponse)
                .bodyToFlux(Branch.class)
                .collectList()
                .map(branches ->{ repo.setBranches(branches); return repo;});

    }

    private Mono<? extends Throwable> handleErrorResponse(ClientResponse response) {
        var statusCode = response.statusCode();
        if (statusCode == HttpStatus.NOT_FOUND) {
            return Mono.error(new NotFoundException("The request data does not exist!"));
        } else {
            return response.createException()
                    .flatMap(error -> Mono.error(new HttpClientErrorException(statusCode)));
        }
    }
}