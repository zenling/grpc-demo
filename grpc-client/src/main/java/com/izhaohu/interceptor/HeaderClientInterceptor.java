package com.izhaohu.interceptor;

import io.grpc.*;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeaderClientInterceptor implements ClientInterceptor {
    private Logger logger = Logger.getLogger(HeaderClientInterceptor.class.getName());
    public static final String INTERCEPTOR_NAME = "HeaderClientInterceptor";
    public static final String USER_ID_KEY = "user_id";
    public static final String TOKEN_KEY = "token";
    private Map<String, String> headerMap;

    public HeaderClientInterceptor(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel channel) {
        logger.info("创建client1");
        final ClientCall<ReqT, RespT> clientCall = channel.newCall(method, callOptions);
        return new ForwardingClientCall<ReqT, RespT>() {
            @Override
            protected ClientCall<ReqT, RespT> delegate() {
                return clientCall;
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                logger.info("拦截器, 此处可以对header 参数进行填充：");
                for (String key: headerMap.keySet()) {
                    Metadata.Key<String> tokenKey = Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
                    headers.put(tokenKey, headerMap.get(key));
                }


                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener ) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        Metadata.Key<String> tokenHeader = Metadata.Key.of(USER_ID_KEY, Metadata.ASCII_STRING_MARSHALLER);
                        String userId =  headers.get(tokenHeader);
                        logger.info("收到userid : "+userId);
                    }
                }, headers);

            }


        };
    }
}
