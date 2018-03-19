FROM openjdk:8-jre-alpine

ADD build/libs/lifetracker.jar app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
