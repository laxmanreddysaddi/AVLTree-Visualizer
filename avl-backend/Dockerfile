FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN javac *.java

EXPOSE 8080

CMD ["sh","-c","java Server"]