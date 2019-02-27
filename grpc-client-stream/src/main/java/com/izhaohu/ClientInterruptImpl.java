package com.izhaohu;

import io.grpc.*;

public class ClientInterruptImpl implements ClientInterceptor {
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel channel) {
        System.out.print("创建client1");
        final ClientCall<ReqT, RespT> clientCall = channel.newCall(method, callOptions);
        return new ForwardingClientCall<ReqT, RespT>() {
            @Override
            protected ClientCall<ReqT, RespT> delegate() {
                return clientCall;
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                System.out.print("拦截器, 此处可以对header 参数进行修改"+" :"+headers);
                Metadata.Key<String> token = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(token, "123456");
                super.start(responseListener, headers);

            }
        };
    }
}
