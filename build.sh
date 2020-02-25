#!/bin/sh

buildApp() {
  echo "Build app"
  ./mvnw package -DskipTests
  echo "Build docker image"
  docker build -t filtering-matches-api .
}

deleteTargetDir() {
  DEST_FOLDER="target/"
  if [ -d "$DEST_FOLDER" ]; then
    echo "Delete '${DEST_FOLDER}' folder"
    rm -R $DEST_FOLDER
  fi
}

deleteMongoVolDir() {
  DEST_FOLDER="mongo-volume/"
  if [ -d "$DEST_FOLDER" ]; then
    echo "Delete '${DEST_FOLDER}' folder"
    rm -R $DEST_FOLDER
  fi
}

deleteContainerImages() {
  DOCKER_IMAGES=`docker images -q -f 'reference=filtering-matches-api*'`
  if [ "$DOCKER_IMAGES" != "" ]; then
    echo "Delete container images"
    docker rmi $(docker images -q -f 'reference=filtering-matches-api*')
  fi
}

stopContainer() {
  RUNNING_CONTAINERS=`docker-compose ps -q`
  if [ "$RUNNING_CONTAINERS" != "" ]; then
    echo "Stop containers"
    docker-compose down
  fi
}

startContainer() {
  DOCKER_IMAGES=`docker images -q -f 'reference=filtering-matches-api*'`
  if [ "$DOCKER_IMAGES" = "" ]; then
    buildApp
  fi
  echo "Start containers"
  docker-compose up -d
}

if [ "$1" = "stop" ]; then
  stopContainer
  exit 0
fi

if [ "$1" = "clean" ]; then
  stopContainer
  deleteTargetDir
  deleteMongoVolDir
  deleteContainerImages
  exit 0
fi

if [ "$1" = "start" ]; then
  startContainer
  exit 0
fi

if [ "$1" = "test" ]; then
  ./mvnw clean test
  exit 0
fi

if [ "$1" = "integrationTest" ]; then
  ./mvnw clean tests -Dspring.profiles.active=integration
  exit 0
fi

buildApp
