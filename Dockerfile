FROM openjdk:11
ADD build/libs/*.jar questionnaire.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","questionnaire.jar"]