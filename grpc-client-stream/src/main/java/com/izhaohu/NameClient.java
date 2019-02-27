package com.izhaohu;

import io.grpc.*;
import io.grpc.examples.nameservers.Ip;
import io.grpc.examples.nameservers.Name;
import io.grpc.examples.nameservers.NameServersGrpc;
import io.grpc.stub.MetadataUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NameClient {
    private static final String DEFAULT_HOST = "localhost";

    private static final int DEFAULT_PORT = 8088;

    private ManagedChannel managedChannel;

    private NameServersGrpc.NameServersBlockingStub nameServersBlockingStub;

    public NameClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build());
    }

    public NameClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
        this.nameServersBlockingStub = NameServersGrpc.newBlockingStub(managedChannel);

        Metadata.Key<String> ATTCHED_HEADER =
                Metadata.Key.of("attached_header", Metadata.ASCII_STRING_MARSHALLER);
        Metadata meta=new Metadata();
        meta.put(ATTCHED_HEADER, "attched");
        this.nameServersBlockingStub = MetadataUtils.attachHeaders(nameServersBlockingStub,meta );
        
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public List<Ip> getIpsByName(String n){
        List<Ip> result = new ArrayList<Ip>();
        Name name = Name.newBuilder().setName(n).build();
        Iterator<Ip> iter = nameServersBlockingStub.getIpsByName(name);
        while (iter.hasNext()) {
            result.add(iter.next());
        }

        return result;
    }

    public static void main(String[] args) throws InterruptedException {

        NameClient nameClient = new NameClient(DEFAULT_HOST,DEFAULT_PORT);

        for(String arg : args){

            List<Ip> result = nameClient.getIpsByName(arg);

            for(int i=0;i<result.size();i++){
                System.out.println("get result from server: " + result.get(i) + " as param is " + arg);
            }


        }
        nameClient.shutdown();

    }

}
