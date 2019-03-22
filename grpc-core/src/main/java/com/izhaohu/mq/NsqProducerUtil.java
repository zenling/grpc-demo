package com.izhaohu.mq;

import com.youzan.nsq.client.Producer;
import com.youzan.nsq.client.ProducerImplV2;
import com.youzan.nsq.client.entity.Message;
import com.youzan.nsq.client.entity.NSQConfig;
import com.youzan.nsq.client.entity.Topic;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;

public class NsqProducerUtil {

   /*private static class NsqUtilHold{
       private static final NsqProducerUtil instance = new NsqProducerUtil();
   }*/

    private NsqProducerUtil() {

        
    }

   /* public static NsqProducerUtil getInstance() {
       return NsqUtilHold.instance;
    }*/

    public static Producer createProducer(String ip, int port) {
        NSQConfig nsqConfig = new NSQConfig();
        nsqConfig.setLookupAddresses(ip +":" + port);
        return new ProducerImplV2(nsqConfig);
    }

    public static void close(Closeable obj) throws IOException {
        if (obj != null) {
            obj.close();
        }
    }

    public static void main(String[] args) throws Exception {

        try {

            ProducerImplV2 producer = null;
            try {
                producer = (ProducerImplV2) NsqProducerUtil.createProducer("10.100.10.216", 4161);
                Topic topic = new Topic("POINT");
                producer.start();
                Message message = Message.create(topic, "100,10".getBytes(Charset.defaultCharset()) );
                producer.publish(message);
            } catch (Exception e) {
                throw e;
            }finally {
                NsqProducerUtil.close(producer);
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e ;
        }
    }
}
