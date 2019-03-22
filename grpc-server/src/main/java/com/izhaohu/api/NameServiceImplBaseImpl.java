package com.izhaohu.api;

import io.grpc.examples.nameserver.*;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NameServiceImplBaseImpl extends NameServiceGrpc.NameServiceImplBase {

    private Map<String, String> map = new HashMap<String, String>();

    private Logger logger = Logger.getLogger(NameServiceImplBaseImpl.class.getName());

    public NameServiceImplBaseImpl() {
        map.put("test", "125.123.123.123");
        map.put("bbb", "152.125.125.125");
    }

    @Override
    public void getIpByName(Name request, StreamObserver<Ip> responseObserver) {
        logger.log(Level.INFO, "request is coming, args= " + request.getName());

//        try{
            Ip ip = Ip.newBuilder().setIp(getName(request.getName())).build();

            responseObserver.onNext(ip);
            responseObserver.onCompleted();
//        } catch (Exception e) {
//            logger.info("onError : {}" + e.getMessage());
//            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription(e.getMessage())));
//        }
    }

    @Override
    public void getComplexObjectByName(Name request, StreamObserver<ComplexObject> responseObserver) {
        Result resultA = Result.newBuilder().setUrl("www.baidu.com").setTitle("baidu").addSnippets("bai").build();
        Result resultB = Result.newBuilder().setUrl("www.tabo.com").setTitle("taobao").addSnippets("tao").build();

        Result resultC = Result.newBuilder().setUrl("www.c.com").setTitle("c").addSnippets("c").build();
        Result resultD = Result.newBuilder().setUrl("www.d.com").setTitle("d").addSnippets("d").build();

        MapValue mv = MapValue.newBuilder().addEle(resultC).addEle(resultD).build();
        ComplexObject cobj = ComplexObject.newBuilder().setId(1)
                .setEmail("test@izhaohu.com")
                .setGender(Gender.MAN)
                .setGenderValue(0)
                .setName(request.getName())
                .addInnerObj(resultA)
                .addInnerObj(resultB)
                .putMapList("map", mv)
                .build();

        responseObserver.onNext(cobj);
        responseObserver.onCompleted();
    }

    public String getName(String name) {
        String ip = map.get(name);
        if (ip == null) {
            return "0.0.0.0";
        }

        return ip;
    }
}
