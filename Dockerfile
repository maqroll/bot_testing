FROM maven:3.5-jdk-8-alpine
COPY ./ /app
WORKDIR /app
#COPY --from=0 /app/vertx /app
RUN mvn install

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=0 /app/target/starter-1.0.0-SNAPSHOT-fat.jar /app
CMD ["java", "-jar", "/app/starter-1.0.0-SNAPSHOT-fat.jar"]
