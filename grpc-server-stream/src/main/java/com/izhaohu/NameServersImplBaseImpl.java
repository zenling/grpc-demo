package com.izhaohu;

import io.grpc.examples.nameservers.Ip;
import io.grpc.examples.nameservers.Name;
import io.grpc.examples.nameservers.NameServersGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NameServersImplBaseImpl extends NameServersGrpc.NameServersImplBase {

    private List<DataType> list = new ArrayList<DataType>();

    public NameServersImplBaseImpl() {
        list.add(new DataType(Name.newBuilder().setName("s").build(), Ip.newBuilder().setIp("127.0.0.1").build()));
        list.add(new DataType(Name.newBuilder().setName("s").build(), Ip.newBuilder().setIp("127.0.0.1").build()));
        list.add(new DataType(Name.newBuilder().setName("a").build(), Ip.newBuilder().setIp("127.0.0.1").build()));
        list.add(new DataType(Name.newBuilder().setName("b").build(), Ip.newBuilder().setIp("127.0.0.1").build()));
        list.add(new DataType(Name.newBuilder().setName("c").build(), Ip.newBuilder().setIp("127.0.0.1").build()));
        list.add(new DataType(Name.newBuilder().setName("d").build(), Ip.newBuilder().setIp("127.0.0.1").build()));
        list.add(new DataType(Name.newBuilder().setName("e").build(), Ip.newBuilder().setIp("127.0.0.1").build()));
        list.add(new DataType(Name.newBuilder().setName("f").build(), Ip.newBuilder().setIp("127.0.0.1").build()));
        
    }

    @Override
    public void getIpsByName(Name request, StreamObserver<Ip> responseObserver) {
        Iterator<DataType> iter = list.iterator();
        while (iter.hasNext()) {
            DataType dt = iter.next();
            if (request.equals(dt.getName())) {
                   System.out.println("get "+ dt.getIp() +" from "+ request);
                   responseObserver.onNext(dt.getIp());
            }
        }

        responseObserver.onCompleted();
    }
}

class DataType {
    private Name name;
    private Ip ip;

    public DataType(Name name, Ip ip) {
        this.name = name;
        this.ip = ip;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Ip getIp() {
        return ip;
    }

    public void setIp(Ip ip) {
        this.ip = ip;
    }

}
