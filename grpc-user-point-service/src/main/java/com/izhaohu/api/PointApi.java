package com.izhaohu.api;

import com.izhaohu.model.Point;
import com.izhaohu.grpc.proto.point.PointApiGrpc;
import com.izhaohu.grpc.proto.point.Response;
import com.izhaohu.service.PointService;
import com.izhaohu.service.serviceimpl.PointServiceImpl;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PointApi extends PointApiGrpc.PointApiImplBase {
    private Logger logger = Logger.getLogger(PointApi.class.getName());
    private PointService pointService = new PointServiceImpl();

    @Override
    public void addPoint(com.izhaohu.grpc.proto.point.Point request, StreamObserver<Response> responseObserver) {
        logger.log(Level.INFO, "add "+ request.getAmount() +"point to user#" + request.getUserId());

        Point point = new Point(request.getAmount(), request.getUserId());
        int result = pointService.addPoint(point);

        Response toReturn = Response.newBuilder().setCode(result).setError("").build();
        responseObserver.onNext(toReturn);
        responseObserver.onCompleted();
    }
}
