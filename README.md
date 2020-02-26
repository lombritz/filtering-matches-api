### Filtering Matches API

The Filtering Matches API provides filtering features over a set of matches.

#### Build Commands

- `./build.sh` build application and docker images.
- `./build.sh clean` delete build files and docker images.

#### Tests

- `./build.sh tests` runs unit tests.
- `./build.sh integrationTests` runs integration tests.

#### API Commands

- `./build.sh start|run` starts the API and all required components. 
- `./build.sh stop` stops the API and all related components.
- `docker-compose logs --follow` follow the logs.

#### Swagger Docs

Run app `./build.sh start` and open http://localhost:8080/swagger-ui.html in your favorite browser. 
