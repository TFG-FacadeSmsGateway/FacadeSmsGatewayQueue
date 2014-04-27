/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.queuesmsgateway.utils;

import com.preferya.queuesmsgateway.models.DataMessageEntity;
import com.preferya.queuesmsgateway.models.IMessageEntity;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;

/**
 *
 * @author Sergio
 */
public class NewTasker {
    
    private String task_queue_name;
    
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    
    public NewTasker(String iso_country) throws IOException{
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        this.connection = this.factory.newConnection();
        this.channel = this.connection.createChannel();

        this.task_queue_name = iso_country;
        
        this.channel.queueDeclare(this.task_queue_name, true, false, false, null);
    }
    
    public void sendMessage(IMessageEntity message) throws IOException{
        this.channel.basicPublish( "", this.task_queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, message.toString().getBytes());
    }
    
    public void close() throws IOException{
        this.channel.close();
        this.connection.close();
    }
    
}
