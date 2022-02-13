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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class RouteConfigTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  public void whenDestinationServerExceedTimeoutReturnTimeOutException() {

    stubFor(get(urlPathEqualTo("/path/api/a"))
        .willReturn(aResponse()
            .withHeader("Content-Type", "application/json")
            .withStatus(200)
            .withFixedDelay(3000)
        )
    );

    webClient
        .get().uri("/service-a/path/api/a")
        .exchange()
        .expectStatus().is5xxServerError();

    stubFor(get(urlPathEqualTo("/path/api/b"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withStatus(200)
                    .withFixedDelay(3000)
            )
    );

    webClient
            .get().uri("/service-b/path/api/b")
            .exchange()
            .expectStatus().is5xxServerError();
  }
}
