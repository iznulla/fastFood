FROM vegardit/graalvm-maven:latest-java11 as builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
COPY --from=builder /app/.env /app/.env
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
