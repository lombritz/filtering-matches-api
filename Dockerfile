FROM openjdk:8-jre
MAINTAINER Jaime Rojas <jaimitorojas@gmail.com>
ADD target/filtering-matches*.jar /usr/share/filtering-matches/filtering-matches.jar

ENTRYPOINT ["java", "-jar", "/usr/share/filtering-matches/filtering-matches.jar"]
