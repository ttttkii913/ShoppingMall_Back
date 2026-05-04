FROM openjdk:17-jdk-bullseye
WORKDIR /app

COPY shoppingmall/gradlew .
COPY shoppingmall/gradlew.bat .
COPY shoppingmall/build.gradle .
COPY shoppingmall/settings.gradle .
COPY shoppingmall/gradle gradle
COPY shoppingmall/src src

RUN apt-get update && apt-get install -y findutils curl bash

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

CMD ["java", "-jar", "build/libs/shoppingmall-0.0.1-SNAPSHOT.jar"]
