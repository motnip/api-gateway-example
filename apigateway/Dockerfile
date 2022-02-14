FROM openjdk:12-jdk-alpine
COPY /build/libs/apigateway-*.jar apigateway.jar
RUN mkdir ./logs
EXPOSE 8080
CMD ["java","-jar","-Dspring.profiles.active=demo","apigateway.jar"]
