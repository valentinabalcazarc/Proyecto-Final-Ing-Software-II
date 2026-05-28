# ─────────────────────────────────────────
# Stage 1 – Build
# ─────────────────────────────────────────
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B


# ─────────────────────────────────────────
# Stage 2 – Runtime
# ─────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

ENV JAVA_OPTS=""

COPY --from=builder /app/target/appointment-service-*.jar app.jar

EXPOSE 8083

USER spring

# ─────────────────────────────────────────
# Espera mayor porque depende de otros servicios
# ─────────────────────────────────────────
ENTRYPOINT ["sh", "-c", "sleep 12 && java $JAVA_OPTS -jar app.jar"]