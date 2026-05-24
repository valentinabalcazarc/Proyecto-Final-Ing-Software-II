# ─────────────────────────────────────────
# Stage 1 – Build (compilación con Maven)
# ─────────────────────────────────────────
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiamos pom primero para aprovechar cache de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos código fuente
COPY src ./src

# Compilamos sin tests para acelerar build
RUN mvn clean package -DskipTests -B


# ─────────────────────────────────────────
# Stage 2 – Runtime (ejecución ligera)
# ─────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear usuario no-root (mejor seguridad)
RUN addgroup -S spring && adduser -S spring -G spring

# Variables JVM opcionales
ENV JAVA_OPTS=""

# Copiamos el jar generado
COPY --from=builder /app/target/api-gateway-*.jar app.jar

EXPOSE 8080

USER spring

# ─────────────────────────────────────────
# Espera antes de iniciar el gateway
# IMPORTANTE:
# El gateway depende de TODOS los microservicios
# por eso se retrasa un poco más
# ─────────────────────────────────────────
ENTRYPOINT ["sh", "-c", "sleep 15 && java $JAVA_OPTS -jar app.jar"]