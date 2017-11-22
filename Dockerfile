FROM clojure:alpine

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY target/blockchain-0.1.1-BURNINGASS-standalone.jar /usr/src/app/

EXPOSE 8090

CMD ["java", "-jar", "blockchain-0.1.1-BURNINGASS-standalone.jar"]
