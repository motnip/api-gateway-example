.PHONY: all gradle-build docker-build docker-run

savingServiceFolder=./savingservice

all: gradle-build docker-build docker-run

#Build of Java code
gradle-build:
	@echo "  >  Building binary of savig service"
#	@cd ./savingservice
#	@pwd
	@cd $(savingServiceFolder) && ./gradlew build

docker-build: gradle-build
	@echo "  >  Building Docker image of savig service"
	@cd $(savingServiceFolder) && docker build -t savings-a . 

docker-run: docker-build
	@echo " > Runnin Docker composer"
	@docker-compose up -d