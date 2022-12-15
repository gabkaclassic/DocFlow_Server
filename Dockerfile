FROM debu99/openjdk15-alpine

WORKDIR ./Docflow

COPY . ./Docflow

RUN apk update
RUN apk add postgresql
RUN apk add sudo

EXPOSE 3000

CMD ["java", "-jar", "Docflow/out/artifacts/Docflow_Server_jar/Docflow_Server.jar"]

