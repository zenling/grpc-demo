package com.izhaohu.interceptor;

import io.grpc.*;
import io.netty.util.internal.StringUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerInterceptor implements io.grpc.ServerInterceptor {
    private Logger logger = Logger.getLogger(ServerInterceptor.class.getName());
    public static final String USER_ID_KEY = "user_id";
    public static final String TOKEN_KEY = "token";

    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata headers, ServerCallHandler<ReqT, RespT> handler) {
        Metadata.Key<String> token = Metadata.Key.of(TOKEN_KEY, Metadata.ASCII_STRING_MARSHALLER);
        final Metadata.Key<String> userIdKey = Metadata.Key.of(USER_ID_KEY, Metadata.ASCII_STRING_MARSHALLER);


        String tokenStr = headers.get(token);
        logger.info("收到客户端token: "+tokenStr+" 长度： "+tokenStr.length() +" ");

        //处理请求头信息是否合法
        if (tokenStr==null || tokenStr.length()==0){
            serverCall.close(Status.DATA_LOSS, headers);
        }

        final String userId = "1000";

        ServerCall<ReqT, RespT> result = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(serverCall) {
            @Override
            public void sendHeaders(Metadata headers) {
                //给客户端返回头信息
                headers.put(userIdKey,userId);
                super.sendHeaders(headers);
            }
        };
        return handler.startCall(result,headers);

    }
}
