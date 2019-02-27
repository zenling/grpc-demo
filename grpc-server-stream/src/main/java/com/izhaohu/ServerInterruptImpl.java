package com.izhaohu;

import io.grpc.*;

public class ServerInterruptImpl  implements ServerInterceptor {

    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        System.out.println("执行server拦截器1,获取token");
        //获取客户端参数
        final Metadata.Key<String> token = Metadata.Key.of("token1", Metadata.ASCII_STRING_MARSHALLER);
        final Metadata.Key<String> attached = Metadata.Key.of("attached_header", Metadata.ASCII_STRING_MARSHALLER);
        final String tokenStr = headers.get(token);
        System.out.println("client attached :" + headers.get(attached));
        if (tokenStr==null || tokenStr.length()==0){
            System.out.println("未收到客户端token,关闭此连接");
            call.close(Status.DATA_LOSS,headers);
        }
        //服务端写回参数
        ServerCall<ReqT, RespT> serverCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendHeaders(Metadata headers) {
                System.out.println("执行server拦截器2,写入token");
                headers.put(token,tokenStr);
                super.sendHeaders(headers);
            }
        };
        return next.startCall(serverCall,headers);
    }
}
