FROM openjdk:8u312-jre-slim

LABEL maintainer="nahwu"
COPY /target/base-0.0.1-SNAPSHOT.jar  /var/tmp/my-base-app/
RUN chmod -R 700 /var/tmp/my-base-app/base-0.0.1-SNAPSHOT.jar
WORKDIR /var/tmp/my-base-app

CMD ["java", "-jar", "base-0.0.1-SNAPSHOT.jar"]
