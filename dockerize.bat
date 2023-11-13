docker compose down;
mvn clean install;
docker build -t bazarik-be .;
docker compose up;