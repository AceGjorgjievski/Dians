FROM maven:3.8.3-openjdk-17 AS build

COPY src /src
COPY pom.xml .

RUN mvn clean package


FROM tomcat:9

COPY --from=0 ./target/application.war /usr/local/tomcat/webapps/application.war

CMD ["catalina.sh", "run"]