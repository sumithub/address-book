# import java
FROM adoptopenjdk/openjdk11:alpine-jre

# Address book target jar file
ARG JAR_FILE=target/*.jar

# working dir
#WORKDIR /opt/app

# cp target/address-book-0.0.1-SNAPSHOT.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]