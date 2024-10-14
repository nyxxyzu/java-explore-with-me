FROM amazoncorretto:21-alpine as buildercd
ARG JAR_FILE=stats-server/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]