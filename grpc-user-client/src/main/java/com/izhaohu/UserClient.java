package com.izhaohu;

import com.izhaohu.grpc.proto.user.Response;
import com.izhaohu.grpc.proto.user.User;
import com.izhaohu.grpc.proto.user.UserApiGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.nameserver.ComplexObject;
import io.grpc.examples.nameserver.Ip;
import io.grpc.examples.nameserver.Name;
import io.grpc.examples.nameserver.NameServiceGrpc;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserClient {
//    private static final String DEFAULT_HOST = "192.168.127.131";
private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8088;
    private ManagedChannel managedChannel;
    private static Logger logger = Logger.getLogger(UserClient.class.getName());

    private NameServiceGrpc.NameServiceBlockingStub nameServiceBlockingStub;
    private UserApiGrpc.UserApiBlockingStub userApi;

    public UserClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build());

    }

    public UserClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
        this.nameServiceBlockingStub = NameServiceGrpc.newBlockingStub(managedChannel);
        this.userApi = UserApiGrpc.newBlockingStub(managedChannel);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Response register(String name, String email, int age) {
        User user = User.newBuilder().setName(name).setEmail(email).setAge(age).build();
        com.izhaohu.grpc.proto.user.Response result = userApi.saveUser(user);
        return result;
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
        UserClient client = new UserClient(DEFAULT_HOST,DEFAULT_PORT );
        Response result = client.register("ceshi", "ceshi@izhaohu.com", 18);
        logger.log(Level.WARNING, result.getCode()+"");
        client.shutdown();
    }


}
