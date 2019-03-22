package com.izhaohu;

import com.izhaohu.api.PointApi;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class PointServer {
    private Logger logger = Logger.getLogger(PointServer.class.getName());

    private static final int DEFAULT_PORT = 8089;

    private int port;

    private Server server;

    public PointServer(int port) {
        this(port, ServerBuilder.forPort(port));
    }

    public PointServer(int port, ServerBuilder<?> serverBuilder) {
        this.port = port;
        //将拦截器注册到server端
        //拦截顺序为 ServerInterceptor ServerInterceptor2
        //server = serverBuilder.addService(ServerInterceptors.intercept(new NameServiceImplBaseImpl(), new ServerInterceptor2(), new ServerInterceptor())).build();
        server = serverBuilder.addService( new PointApi()).build();
    }

    private void start() throws IOException {
        server.start();
        logger.info("server has started , listen on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                PointServer.this.stop();
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
        PointServer pointService;
        if (args.length > 0) {
            pointService = new PointServer(Integer.parseInt(args[0]));
        }else{
            pointService = new PointServer(DEFAULT_PORT);
        }

        pointService.start();
        pointService.blockUntilShutdown();
    }


    
}

