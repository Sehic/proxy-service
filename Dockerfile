FROM adoptopenjdk/openjdk11:latest
ENV PORT 8081
EXPOSE 8081
RUN apt-get update \
  && apt-get install -y wget \
  && rm -rf /var/lib/apt/lists/*
RUN mkdir -p /app/
ADD build/libs/proxy.jar /app/app.jar
CMD ["java", "-jar", "app/app.jar"]