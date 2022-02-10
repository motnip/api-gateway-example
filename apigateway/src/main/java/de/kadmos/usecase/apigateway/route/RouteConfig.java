package de.kadmos.usecase.apigateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

  @Value("${destination.uri}")
  private String url;

  @Bean
  public RouteLocator getRoutes(RouteLocatorBuilder builder) {

    return builder.routes()
        .route(p -> p
            .path("/droid")
            .filters(gatewayFilterSpec -> gatewayFilterSpec
                .setPath("/api/species/2/")
                .addRequestHeader("accept", "application/json")
            )
            .uri(url)
        )
        .route(p -> p
            .path("/wookie")
            .filters(gatewayFilterSpec -> gatewayFilterSpec
                .setPath("/api/species/3/")
                .addRequestHeader("accept", "application/json")
            )
            .uri(url)
        )
        .build();
  }
}
