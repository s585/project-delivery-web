FROM maven:3-openjdk-16-slim AS build
WORKDIR /app
COPY . .
RUN mvn -B package

FROM tomcat:9-jdk16-openjdk-slim
COPY --from=build /app/target/project-delivery-web-1.0.war $CATALINA_HOME/webapps/ROOT.war
EXPOSE 8080