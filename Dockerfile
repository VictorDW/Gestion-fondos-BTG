FROM openjdk:17-jdk-slim
LABEL author="Victor Agudelo"
ARG JAR_FILE=build/libs/btg-0.0.1.jar
COPY ${JAR_FILE} btg.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","btg.jar"]