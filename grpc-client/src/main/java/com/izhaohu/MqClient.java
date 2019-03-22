package com.izhaohu;

import com.izhaohu.grpc.proto.point.Point;
import com.izhaohu.grpc.proto.point.PointApiGrpc;
import com.izhaohu.interceptor.HeaderClientInterceptor;
import com.izhaohu.mq.NsqConsumerUtil;
import com.youzan.nsq.client.Consumer;
import com.youzan.nsq.client.MessageHandler;
import com.youzan.nsq.client.entity.NSQMessage;
import com.youzan.nsq.client.exception.NSQException;
import io.grpc.*;
import io.grpc.examples.nameserver.*;
import io.grpc.stub.MetadataUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MqClient {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 8089;
    private ManagedChannel managedChannel;
    private static Logger logger = Logger.getLogger(MqClient.class.getName());
    private PointApiGrpc.PointApiBlockingStub pointApi;
    private Consumer nsqConsumer;

    public MqClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build());

    }

    public MqClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
        this.pointApi = PointApiGrpc.newBlockingStub(managedChannel);
        try {
            nsqConsumer = NsqConsumerUtil.creatConsumer("base", "10.100.10.216", 4161, "POINT",
                    new MessageHandler() {
                        @Override
                        public void process(NSQMessage nsqMessage) {
                            logger.log(Level.WARNING, "received point msg");
                            String content = nsqMessage.getReadableContent();
                            String[] params = content.split(",");
                            addPoint(Integer.parseInt(params[0]), Integer.parseInt(params[1]));

                        }
                    }, true);
        } catch (NSQException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "nsq consumer create failed");
        }
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
//        NsqConsumerUtil.close(nsqConsumer);
    }

    public void addPoint(int amount, int userId) {
        logger.log(Level.WARNING, "call point service ...");
        Point point = Point.newBuilder().setAmount(amount).setUserId(userId).build();
        pointApi.addPoint(point);
        logger.log(Level.WARNING, "call point service end  ...");
    }

    public static void main(String[] args) throws InterruptedException {
        MqClient client = new MqClient(DEFAULT_HOST,DEFAULT_PORT );
        client.addPoint(10, 10);
        logger.log(Level.WARNING, "start mq client ...");
        //client.shutdown();
    }


}
