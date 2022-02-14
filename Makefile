.PHONY: all gradle-saving gradle-gateway docker-saving docker-gateway  docker-run

savingServiceFolder=./savingservice
apigatewayFolder=./apigateway

all: gradle-saving gradle-gateway docker-saving docker-gateway  docker-run

#--- SERVICE-A RUNNER ---
gradle-saving:
	@echo "  >  Building binary of savig service"
	@cd $(savingServiceFolder) && ./gradlew build

docker-saving: gradle-saving
	@echo "  >  Building Docker image of savig service"
	@cd $(savingServiceFolder) && docker build -t savings .

#--- API GATEWAY RUNNER ---
gradle-gateway:
	@echo "  >  Building binary of api gateway"
	@cd $(apigatewayFolder) && ./gradlew build

docker-gateway: gradle-gateway
	@echo "  >  Building Docker image of savig service"
	@cd $(apigatewayFolder) && docker build -t api-gateway .

#--- DEMO RUNNER ---
docker-run: gradle-saving gradle-gateway docker-saving docker-gateway
	@echo " > Runnin Docker composer"
	@docker-compose up -d
