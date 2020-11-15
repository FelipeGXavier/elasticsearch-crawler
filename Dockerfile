FROM adoptopenjdk/openjdk11

ADD target/journal-search-*.jar /app/app.jar

COPY dev-config.yml /app

WORKDIR /app

RUN java -version

CMD ["java", "-jar", "app.jar", "server", "dev-config.yml"]

ENV JAVA_OPTS="-XX:PermSize=1024m -XX:MaxPermSize=1024m"

EXPOSE 8080-8081