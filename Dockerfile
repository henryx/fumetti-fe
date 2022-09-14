FROM maven:3-openjdk-18 AS build-env
COPY . /app
WORKDIR /app
RUN --mount=type=cache,target=/root/.m2 mvn -Pproduction clean package

FROM openjdk:18-slim
COPY --from=build-env /app/target/quarkus-app/ /app
WORKDIR /app

ENV QUARKUS_HTTP_HOST=0.0.0.0
ENV QUARKUS_HTTP_PORT=8080

EXPOSE 8080
CMD ["java", "-jar", "/app/quarkus-run.jar"]
