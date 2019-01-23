FROM hseeberger/scala-sbt:latest
RUN wget https://github.com/mhsg67/docker-playground/archive/master.zip
RUN unzip master.zip
WORKDIR docker-playground-master/
RUN sbt assembly
ENTRYPOINT ["java", "-jar", "/root/docker-playground-master/target/scala-2.11/docker-playground-assembly-0.1.jar", "tester", "kafka:9092", "http://schema-registry:8081", "test-topic"]
