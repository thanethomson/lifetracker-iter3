FROM openjdk:8-jre-stretch

ADD build/libs/lifetracker.jar app.jar
RUN touch app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
