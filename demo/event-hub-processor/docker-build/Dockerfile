FROM openjdk:11

# Copy start script
ADD ./start-script /usr/local
RUN chmod a+x /usr/local/start-container.sh

ENV JAVA_OPTS="-Xmx512M"

CMD /usr/local/start-container.sh

COPY ./java/event-hub-proccesor-0.0.1-SNAPSHOT.jar /opt/app/app.jar
