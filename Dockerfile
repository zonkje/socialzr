FROM adoptopenjdk/openjdk14
EXPOSE 8080
ADD target/*.jar socialzr.jar
ENTRYPOINT ["java", "-jar", "socialzr.jar"]