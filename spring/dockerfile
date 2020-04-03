FROM openjdk:8-jre-alpine
# copy application JAR (with libraries inside)
COPY application/target/application-*.jar /application.jar
# specify default command
CMD ["/usr/bin/java", "-jar", "/application.jar", "--spring.datasource.url=${DB_URL}", "--spring.datasource.username=${DB_USER}", "--spring.datasource.password=${DB_PASSWORD}"]
