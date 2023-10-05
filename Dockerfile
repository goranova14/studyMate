FROM gradle:7.5-jdk17
WORKDIR /opt/app
COPY ./build/libs/StudyMate-0.0.1-SNAPSHOT.jar ./
EXPOSE 8080

ENTRYPOINT ["sh", "-c","java ${JAVA_OPTS} -jar StudyMate-0.0.1-SNAPSHOT.jar "]