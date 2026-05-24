# ─────────────────────────────────────────
# Stage 1 – Build (compilación)
# ─────────────────────────────────────────
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiamos solo pom primero para aprovechar cache de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos código fuente
COPY src ./src

# Compilamos el proyecto sin tests
RUN mvn clean package -DskipTests -B


# ─────────────────────────────────────────
# Stage 2 – Runtime (ejecución)
# ─────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Usuario no root (seguridad)
RUN addgroup -S spring && adduser -S spring -G spring

# Variables JVM opcionales
ENV JAVA_OPTS=""

# Copiamos el jar construido
COPY --from=builder /app/target/auth-service-*.jar app.jar

EXPOSE 8081

USER spring

# ─────────────────────────────────────────
# Espera ligera antes de arrancar Spring
# (evita arranque demasiado rápido en redes Docker)
# ─────────────────────────────────────────
ENTRYPOINT ["sh", "-c", "sleep 5 && java $JAVA_OPTS -jar app.jar"]