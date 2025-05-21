FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /backend
RUN groupadd spring && adduser -g spring spring
COPY pom.xml pom.xml
RUN mvn dependency:resolve
COPY src/main src/main
RUN cat /backend/src/main/resources/application.yml
RUN mvn clean package && ls -ltr

FROM openjdk:17
WORKDIR /backend
RUN groupadd spring && adduser -g spring spring
COPY --from=builder /backend/target/my_personal_finance_website_backend-0.0.1-exec.jar personal-finance-backend.jar
RUN chown -R spring:spring /backend && chmod -R 700 /backend && ls -ltr && pwd
USER spring
EXPOSE 8090
ENTRYPOINT ["java","-jar","personal-finance-backend.jar"]