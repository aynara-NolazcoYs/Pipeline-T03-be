FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
COPY src src
RUN chmod +x mvnw && ./mvnw -DskipTests clean package

FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Admin12345" -p 1433:1433 --name sqlserver -d pierosotelo/sql-server:2022


# docker build -t pierosotelo/springboot-sqlserver:1.0 .

#mvn clean package -DskipTests

# docker run -d --name springboot-sqlserver -p 8085:8085 pierosotelo/springboot-sqlserver:1.0

# docker push pierosotelo/springboot-sqlserver:1.0