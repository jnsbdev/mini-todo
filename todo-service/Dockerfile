# Build
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /src

# better cache: resolve deps first
COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline

# now save sources and build
COPY src ./src
RUN mvn -q -B -DskipTests package


# Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /src/target/*.jar app.jar

# run as non-root (optional but recommended)
RUN addgroup -S app && adduser -S app -G app
USER app

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
