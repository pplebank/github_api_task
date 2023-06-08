package com.pplebank.github_api_task;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.pplebank.github_api_task.Entity.GithubRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


import static com.github.tomakehurst.wiremock.client.WireMock.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubApiTaskApplicationTest {
    private static WebTestClient webTestClient;
    private static WireMockServer wireMockServer;


    @BeforeAll
    public static void setup(){
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + wireMockServer.port())
                .build();

        stubFor(get(urlEqualTo("/users/existing_user/repos"))
                .withHeader("Accept", containing("application/json"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("repositories.json")));

        stubFor(get(urlPathMatching("/repos/.*"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("branches.json")));
    }

    @AfterAll
    public static void teardown() {
        wireMockServer.stop();
    }
    @Test
    public void testListRepositoriesSuccess() {

        webTestClient.get()
                .uri("/users/existing_user/repos")
                .header("Accept", "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GithubRepository.class)
                .hasSize(2);
    }

    @Test
    public void testListRepositoriesNoUserFailed() {

        webTestClient.get()
                .uri("/users/non_existing_user/repos")
                .header("Accept", "application/json")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void testListRepositoriesNoJsonHeaderFailed() {

        webTestClient.get()
                .uri("/users/existing_user/repos")
                .header("Accept", "application/xml")
                .exchange()
                .expectStatus().is4xxClientError();
    }

}
