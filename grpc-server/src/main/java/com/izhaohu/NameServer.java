package com.izhaohu;

import com.izhaohu.interceptor.ServerInterceptor;
import com.izhaohu.interceptor.ServerInterceptor2;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;

import java.io.IOException;
import java.util.logging.Logger;

public class NameServer {
    private Logger logger = Logger.getLogger(NameServer.class.getName());

    private static final int DEFAULT_PORT = 8088;

    private int port;

    private Server server;

    public NameServer(int port) {
        this(port, ServerBuilder.forPort(port));
    }

    public NameServer(int port, ServerBuilder<?> serverBuilder) {
        this.port = port;
        //将拦截器注册到server端
        //拦截顺序为 ServerInterceptor ServerInterceptor2
        server = serverBuilder.addService(ServerInterceptors.intercept(new NameServiceImplBaseImpl(), new ServerInterceptor2(), new ServerInterceptor())).build();
        //server = serverBuilder.addService(new NameServiceImplBaseImpl()).build();
    }

    private void start() throws IOException {
        server.start();
        logger.info("server has started , listen on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                NameServer.this.stop();
            }
        });
    }

    private void stop() {
        logger.info("server has stopped");
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        while(true) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        NameServer nameServer;
        if (args.length > 0) {
            nameServer = new NameServer(Integer.parseInt(args[0]));
        }else{
            nameServer = new NameServer(DEFAULT_PORT);
        }

        nameServer.start();
        nameServer.blockUntilShutdown();
    }


    
}

