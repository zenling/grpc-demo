FROM openjdk:latest

VOLUME ["/opt/app/logs"]

ENV logdir /opt/app/logs

COPY grpc-user-point-service-1.0.jar /opt/app/grpc-user-point-service-1.0.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

EXPOSE 8089

WORKDIR /opt/app

CMD java -jar grpc-user-point-service-1.0.jar
