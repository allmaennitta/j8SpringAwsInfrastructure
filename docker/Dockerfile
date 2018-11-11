FROM openjdk:8-jre-alpine
VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} app.jar
#ADD ./.aws /root/.aws
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xmx50m", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:MaxRAMFraction=1", "-jar", "/app.jar"]