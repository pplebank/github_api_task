package com.pplebank.github_api_task;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.pplebank.github_api_task.Entity.GithubRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


import static com.github.tomakehurst.wiremock.client.WireMock.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubApiTaskApplicationTest {
    private WebTestClient webTestClient;

    @Test
    public void testListRepositories() {
        var wireMockServer = new WireMockServer();
        wireMockServer.start();

        stubFor(get(urlEqualTo("/users/pplebank/repos"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("repositories.json")));

        stubFor(get(urlPathMatching("/repos/.*"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("branches.json")));


        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + wireMockServer.port())
                .build();

        webTestClient.get()
                .uri("/users/pplebank/repos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GithubRepository.class);
        wireMockServer.stop();

    }


}
