/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.queuesmsgateway.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;

/**
 *
 * @author Sergio
 */
public class Worker {
    
    private static final String TASK_QUEUE_NAME = "internalQueue";
    
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private QueueingConsumer consumer;
    
    public Worker() throws IOException{
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        this.connection = this.factory.newConnection();
        this.channel = this.connection.createChannel();

        this.channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        this.channel.basicQos(1);

        this.consumer = new QueueingConsumer(this.channel);
        this.channel.basicConsume(TASK_QUEUE_NAME, false, this.consumer);
    }
    
    public String receivedMessage() throws InterruptedException, IOException{
        QueueingConsumer.Delivery _delivery = consumer.nextDelivery();
        String _message = new String(_delivery.getBody());
        this.channel.basicAck(_delivery.getEnvelope().getDeliveryTag(), false);
        
        return _message;
    }
    
    public void close() throws IOException{
        this.channel.close();
        this.connection.close();
    }
}
