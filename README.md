# grpc-demo

 1. 测试list map 
 2. 测试grpc interceptor

grpc

数据库 TIDB
消息队列 NSQ

## module grpc-base 
定义 grpc proto 文件

## module grpc-core
数据库连接组件 支持 mybatis hibernate
nsq 封装util

## module grpc-server
用户service ，当用户注册时，发送积分消息到nsq

## module grpc-user-point-ser
用户积分service, 

## module grpc-client
mq 消费者 ， 消费 积分消息， 调用用户积分service给用户添加积分 

## module grpc-user-client
客户端 模拟app 发起用户注册请求。

# 下一步
    - 添加参数配置

