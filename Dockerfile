FROM openjdk:8
EXPOSE 8086
VOLUME /temp
ADD target/ePharmacy.jar ePharmacy.jar
ENTRYPOINT ["java","-jar","ePharmacy.jar"]