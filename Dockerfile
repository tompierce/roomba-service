FROM java:8-jdk
CMD mkdir /code
ADD . /code
WORKDIR /code
CMD ./gradlew run

