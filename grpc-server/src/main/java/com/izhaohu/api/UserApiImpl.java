package com.izhaohu.api;

import com.izhaohu.grpc.proto.user.Response;
import com.izhaohu.grpc.proto.user.User;
import com.izhaohu.grpc.proto.user.UserApiGrpc.UserApiImplBase;
import com.izhaohu.mq.NsqProducerUtil;
import com.izhaohu.service.UserService;
import com.izhaohu.service.serviceimpl.UserServiceImpl;
import com.youzan.nsq.client.ProducerImplV2;
import com.youzan.nsq.client.entity.Message;
import com.youzan.nsq.client.entity.Topic;
import com.youzan.nsq.client.exception.NSQException;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserApiImpl extends UserApiImplBase {
    private Logger logger = Logger.getLogger(UserApiImpl.class.getName());
    private UserService userService = new UserServiceImpl();
    private ProducerImplV2 nsqProducer = (ProducerImplV2) NsqProducerUtil.createProducer("10.100.10.216", 4161);

    public UserApiImpl() {
        try {
            nsqProducer.start();
        } catch (NSQException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "nsq producer start failed");
        }
    }

    @Override
    public void saveUser(User request, StreamObserver<Response> responseObserver) {
        logger.log(Level.INFO, "regist user, name= " + request.getName());
        logger.log(Level.INFO, "regist user, email= " + request.getEmail());
        logger.log(Level.INFO, "regist user, age= " + request.getAge());

        com.izhaohu.model.User user = new com.izhaohu.model.User(request.getName(), request.getAge(),
                request.getEmail());
        int result = userService.addUser(user);

        Response toReturn = Response.newBuilder().setCode(result).setError("").build();
        responseObserver.onNext(toReturn);
        responseObserver.onCompleted();

        try {
            publicAddPointMsg();
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "nsq send add point failed");
        }
    }

    public void publicAddPointMsg() throws NSQException {

        try {
            logger.log(Level.WARNING, "nsq send add point msg");
            Topic topic = new Topic("POINT");
            nsqProducer.start();
            Message message = Message.create(topic, "10,1".getBytes(Charset.defaultCharset()) );
            nsqProducer.publish(message);
        } catch (Exception e) {
            throw e;
        }finally {
            try {
                NsqProducerUtil.close(nsqProducer);
            } catch (IOException e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "nsq consumer close failed");
            }
        }

    }
}
