FROM openjdk:12-jdk-alpine
COPY /build/libs/savingservice-0.0.1-*.jar savingservice.jar
EXPOSE 9001
CMD ["java","-jar","-Dspring.profiles.active=demo","savingservice.jar"]
