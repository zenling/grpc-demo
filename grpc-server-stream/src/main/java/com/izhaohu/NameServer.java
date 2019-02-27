package com.izhaohu;

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

    public NameServer(int port, ServerBuilder<?> serverBuilder) {
        this.port = port;
        server = serverBuilder.addService(new NameServersImplBaseImpl()).build();
    }

    public NameServer(int port) {
        this(port, ServerBuilder.forPort(port));
    }

    private void start() throws IOException {
        server.start();
        logger.info("server has started , listenning on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                NameServer.this.stop();
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
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
