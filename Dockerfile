FROM openjdk:21-ea-oracle
EXPOSE 8080
ADD target/classroom-service.jar classroom-service.jar
ENTRYPOINT ["java","-jar","/classroom-service.jar"]
