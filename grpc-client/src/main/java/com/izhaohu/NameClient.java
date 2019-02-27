package com.izhaohu;

import com.izhaohu.interceptor.HeaderClientInterceptor;
import io.grpc.*;
import io.grpc.examples.nameserver.*;
import io.grpc.stub.MetadataUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NameClient {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8088;
    private ManagedChannel managedChannel;

    private NameServiceGrpc.NameServiceBlockingStub nameServiceBlockingStub;

    public NameClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build());

    }

    public NameClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put(HeaderClientInterceptor.TOKEN_KEY, "123456");
        //生成含拦截器的channel
        Channel channel = ClientInterceptors.intercept(managedChannel, new HeaderClientInterceptor(headerMap));
        //this.nameServiceBlockingStub = NameServiceGrpc.newBlockingStub(channel);

        Metadata.Key<String> ATTCHED_HEADER =
                Metadata.Key.of("attached_header", Metadata.ASCII_STRING_MARSHALLER);
        Metadata meta=new Metadata();
        meta.put(ATTCHED_HEADER, "attched");
        this.nameServiceBlockingStub = MetadataUtils.attachHeaders(NameServiceGrpc.newBlockingStub(channel), meta);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String getIpByName(String n) {
        Name name = Name.newBuilder().setName(n).build();
        Ip ip = nameServiceBlockingStub.getIpByName(name);
        return ip.getIp();
    }

    public ComplexObject getComplexObjByName(String nameStr) {
        Name name = Name.newBuilder().setName(nameStr).build();
        ComplexObject ob = nameServiceBlockingStub.getComplexObjectByName(name);
        return ob;
    }

    public static void main(String[] args) throws InterruptedException {
        NameClient client = new NameClient(DEFAULT_HOST,DEFAULT_PORT );
        for (String arg : args) {
            String res = client.getIpByName(arg);
            System.out.println("get result from server "+ res +" as name is "+ arg);

            ComplexObject co = client.getComplexObjByName(res);
            Gender gender = co.getGender();
            List<Result> inner = co.getInnerObjList();
            System.out.println("-----------------inner--------------------------");
            for (Result r: inner ) {
                System.out.print(r);
            }

            System.out.println("--------------mapValue---------------------");
            Map<String, MapValue> mapValue = co.getMapListMap();
            for (String key: mapValue.keySet()) {
                System.out.println("key : " +key);
                System.out.println("value : "+mapValue.get(key).getEleList());
            }

        }
        client.shutdown();
    }


}
