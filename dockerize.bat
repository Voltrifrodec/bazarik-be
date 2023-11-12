docker-compose down
@REM mvn clean install
@REM docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
docker build -t bazarik-be .
docker-compose up