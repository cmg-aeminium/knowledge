FROM payara/micro:jdk11

COPY target/sweranker-1.0.war sweranker-1.0.war

COPY src/main/resources/pre-boot-commands.txt pre-boot-commands.txt

ENTRYPOINT ["java","-jar", "payara-micro.jar", "--deploy", "sweranker-1.0.war" ,"--prebootcommandfile" , "pre-boot-commands.txt"]