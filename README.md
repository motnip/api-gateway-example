
# Kadmos Usecas


## Api Gateway

The API Gateway has been developed using  Spring Cloud Gateway which offers a simple way to route to the correct API the incoming request.

Moreover, thanks to filters, it is possible to develop other functionalities like checking tokens or logging data.

With Spring API Gateway, it is possible to implement the routes in two ways:
- YAML file
- java code.

I chose the last one because it makes easy to write tests. 

I developed a unit test that verifies a time-out exception is thrown when the response time out goes over 5 seconds.

The API-Gateway logs the incoming request on file in a folder named logs.

######Gateway log

The dockerize API Gateway has a docker volume, mapped on a local folder, where the logs file is issued. 
Therefore, it is easier to access the log file.

In the root project a folder named logs is mapped as volume to the dockerized API Gateway.

## Scale up the Gateway

The API Gateway is a fundamental component in the system.
It is responsible for authenticating, authorizing, transforming, and routing requests to appropriate services.

Scaling depends on the number of incoming requests, the throughout, number of errors.

Before to decide a scale strategy we have to consider, for instance, the number of endpoints and the other work (authorizing, for example ) it is in charge of, along with an average number of incoming requests. 

Assuming we deploy this service on K8s, we can set a proper CPU and memory(vertical scaling). Those values could be scaled up once a certain threshold is reached. 

Eventually, we can define a given number of pods (horizontal scaling) that could be scaled up considering the number of incoming requests the service can serve in a given period as well as considering the number of times the API returns a specific error HTTP code (e.g. 504)

## Monitoring the API Gateway

Spring Cloud API Gateway (Spring boot in general) has integrated the actuator API, exposing parameters describing the status health of the system.

Those values can be collected by Prometheus and then defined some alters.

Moreover, we can expose the health endpoints that says if the components are up and running. 

## Savings Services

I developed one service. The project name is "savingservice".
Saving Service B is a clone of Saving Service A, except for the port and the database connection. 

Therefore, I define (spring) profiles by two application properties files named:
- demoa the application properties for Service A 
- demob the application properties for Service B.

In the docker-compose  file I defined two services:
- saving-service-a:  running with the spring profile "demoa"
- saving-service-b:  running with the spring profile "demob"

We could pass those params (like port and DB connections) as an environment variable in a production scenario. I think the profile feature is more convenient for this case study.

Each profile runs a SQL script inserting users and a checking account into the DB of each service.

## Note about the URL Path of the services

You will see that the URL Paths are different from those drawn by the Sequence Diagram in the case study.
I was not sure if I had to follow that instruction, I decided to go with the Restfull path like this:

http://localhost:[port]/savings/users/{{userId}}/accounts/{{accountNumber}}/[balance|deposit|withdraw]

## API Reference

The API Gateway answers to: http://localhost:8080/service-[a or b]/**

#### Get Balance

```http
  GET /savings/users/{{userId}}/accounts/{{accountNumber}}/balance
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `userId` | `Integer` | It is the user id. As defualt there are one user with ID 1 per service
| `accountNumber` | `String` | It is the account number. As default there are one per user. Account A for service A, B for the service B |

#### Withdraw

```http
  /savings/users/{{userId}}/accounts/{{accountNumber}}/withdraw?amount=2000.12
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `amount`      | `BigDecimal` | **Required**. it is the amount to withdraw |

#### Deposit

```http
  /savings/users/{{userId}}/accounts/{{accountNumber}}/deposit?amount=2000.12
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `amount`      | `BigDecimal` | **Required**. it is the amount to deposit |


## Running Tests

I dockerized all the components.

In the root folder of the project, there is a Docker compose.

In the docker-compose file there are five services:
- kadmos-data-a: database Postgres for the serivice A
- kadmos-data-B: database Postgres for the serivice B
- saving-service-a
- saving-service-b
- gateway: the API gateway

A makefile is set to:
- compile the java code of of api-gateway and savingsservice
- build the Docker images of api-gateway and savingsservice.
- run the docker-compose file

You need to type the command make in the shell to build and run all the components.

```bash
  make
```

The makefile provides commands to build a specific components.

For instance:
```bash
  make docker-gateway
```
Compile the java code and build the Docker images of the API Gateway.

###### Test users

Service A has one users with ID 1 and Account number A
Service B has one users with ID 1 and Account number B

You can use those users to test the project.

