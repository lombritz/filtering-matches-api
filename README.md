### Filtering Matches API

The Filtering Matches API provides filtering features over a set of matches.

#### Build Commands

- `./build.sh` build application and docker images.
- `./build.sh clean` delete build files and docker images.

#### Tests

- `./build.sh tests` execute unit tests.
- `./build.sh integrationTests` execute integration tests.

#### API Commands

- `./build.sh start` start the API with necessary containers. 
- `./build.sh stop` stop the API and all related containers 
- `docker-compose logs --follow` follow the logs.

#### Swagger Docs

Run app `./build.sh start` and open http://localhost:8080/swagger-ui.html in your favorite browser. 
