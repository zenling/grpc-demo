package com.izhaohu.mq;

import com.youzan.nsq.client.Consumer;
import com.youzan.nsq.client.ConsumerImplV2;
import com.youzan.nsq.client.MessageHandler;
import com.youzan.nsq.client.entity.NSQConfig;
import com.youzan.nsq.client.entity.NSQMessage;
import com.youzan.nsq.client.exception.NSQException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NsqConsumerUtil {

    public static Consumer creatConsumer(String channel, String lookupAddress, int lookupPort,String topic, MessageHandler handler, boolean autoFinish) throws NSQException {
        NSQConfig config = new NSQConfig(channel);
        config.setLookupAddresses(lookupAddress + ":" + lookupPort);
        ConsumerImplV2 consumer = new ConsumerImplV2(config, handler);
        consumer.subscribe(topic);
        consumer.setAutoFinish(autoFinish);
        consumer.start();
        return consumer;
    }

    public static void close(Consumer consumer) {
        if (consumer != null) {
            consumer.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
       // final CountDownLatch latch = new CountDownLatch(1);
        MessageHandler handler = new MessageHandler() {
            @Override
            public void process(NSQMessage nsqMessage) {
                System.out.println(nsqMessage.getReadableContent());
               // latch.countDown();
            }
        };
        Consumer consumer = null;
        try {
            consumer = NsqConsumerUtil.creatConsumer("base", "10.100.10.216", 4161, "POINT", handler, true);
        } catch (NSQException e) {
            e.printStackTrace();
        }finally {
            //latch.await(5, TimeUnit.SECONDS);
            //NsqConsumerUtil.close(consumer);
        }


        Thread.sleep(500000);
    }

    
    
}
