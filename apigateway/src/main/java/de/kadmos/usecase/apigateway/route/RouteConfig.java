package de.kadmos.usecase.apigateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

  @Value("${service.a.uri}")
  private String serviceA;
  @Value("${service.b.uri}")
  private String serviceB;

  @Bean
  public RouteLocator getRoutes(RouteLocatorBuilder builder) {

    return builder
        .routes()
        .route(
            p ->
                p.path("/service-a/**")
                    .filters(gatewayFilterSpec -> gatewayFilterSpec.rewritePath("service-a", ""))
                    .uri(serviceA))
        .route(
            p ->
                p.path("/service-b/**")
                    .filters(gatewayFilterSpec -> gatewayFilterSpec.rewritePath("service-b", ""))
                    .uri(serviceB))
        .build();
  }
}
