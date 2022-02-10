package de.kadmos.usecase.apigateway.route;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"url=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class RouteConfigTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void whenDestinationServerExceedTimeoutReturnTimeOutException() {

    stubFor(get(urlPathEqualTo("/api/species/2/"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)
            .withFixedDelay(3000)
        )
    );

    webClient
        .get().uri("/droid")
        .exchange()
        .expectStatus().is5xxServerError();

    stubFor(get(urlPathEqualTo("/api/species/3/"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)
            .withFixedDelay(3000)
        )
    );

    webClient
        .get().uri("/wookie")
        .exchange()
        .expectStatus().is5xxServerError();
  }
}
