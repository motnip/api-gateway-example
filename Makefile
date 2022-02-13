.PHONY: all gradle-build-a gradle-build-apigateway docker-build-a docker-build-gateway  docker-run

savingServiceFolder=./savingservice
apigatewayFolder=./apigateway

all: gradle-build-a gradle-build-apigateway docker-build-a docker-build-gateway  docker-run

#--- SERVICE-A RUNNER ---
gradle-build-a:
	@echo "  >  Building binary of savig service"
	@cd $(savingServiceFolder) && ./gradlew build

docker-build-a: gradle-build-a
	@echo "  >  Building Docker image of savig service"
	@cd $(savingServiceFolder) && docker build -t savings-a . 

#--- API GATEWAY RUNNER ---
gradle-build-gateway:
	@echo "  >  Building binary of api gateway"
	@cd $(apigatewayFolder) && ./gradlew build

docker-build-gateway: gradle-build-gateway
	@echo "  >  Building Docker image of savig service"
	@cd $(apigatewayFolder) && docker build -t api-gateway . 

#--- DEMO RUNNER ---
docker-run: gradle-build-a gradle-build-apigateway docker-build-a docker-build-gateway
	@echo " > Runnin Docker composer"
	@docker-compose up -d