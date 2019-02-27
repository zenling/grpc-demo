package com.izhaohu.interceptor;

import io.grpc.*;

import java.util.logging.Logger;

public class ServerInterceptor2 implements io.grpc.ServerInterceptor {
    private Logger logger = Logger.getLogger(ServerInterceptor2.class.getName());
    public static final String USER_ID_KEY = "user_id";
    public static final String TOKEN_KEY = "token";

    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata headers, ServerCallHandler<ReqT, RespT> handler) {
        Metadata.Key<String> token = Metadata.Key.of(TOKEN_KEY, Metadata.ASCII_STRING_MARSHALLER);
        final Metadata.Key<String> userIdKey = Metadata.Key.of(USER_ID_KEY, Metadata.ASCII_STRING_MARSHALLER);


        String tokenStr = headers.get(token);
        logger.info("收到客户端token2: "+tokenStr+" 长度2： "+tokenStr.length() +" ");

        //处理请求是否合法
        if (tokenStr==null || tokenStr.length()==0){
            serverCall.close(Status.DATA_LOSS, headers);
        }

        return handler.startCall(serverCall,headers);

    }
}
