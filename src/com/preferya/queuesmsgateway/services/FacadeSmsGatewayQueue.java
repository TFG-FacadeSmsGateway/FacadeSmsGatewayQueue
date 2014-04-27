/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.queuesmsgateway.services;

import com.preferya.queuesmsgateway.models.ControlMessageEntity;
import com.preferya.queuesmsgateway.utils.NewTasker;
import com.preferya.queuesmsgateway.utils.Worker;
import com.preferya.queuesmsgateway.models.DataMessageEntity;
import com.preferya.queuesmsgateway.models.IMessageEntity;
import java.io.IOException;

/**
 *
 * @author Sergio
 */
public class FacadeSmsGatewayQueue {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Worker _worker = new Worker();
        
        boolean stop = false;
        
        while(!stop) {
            String _rcvMessage = _worker.receivedMessage();
            
            IMessageEntity _message = splitAndConvertToMessageEntity(_rcvMessage);
            
            //Delete this linea when I'll test this program.
            System.out.println(_rcvMessage);
            
            if(_message instanceof DataMessageEntity) { //Data message case
                NewTasker _newTasker = new NewTasker(_message.getIsoLang());
                _newTasker.sendMessage(_message);
                _newTasker.close();
                System.out.println("Message sended");
            }else { //Control message case
                if(_message.getAction() != null && _message.getAction().equalsIgnoreCase("stop")) { //action stop case
                    stop = true; //Stop while
                    System.out.println("STOP");
                    NewTasker _newTasker = new NewTasker(_message.getIsoLang());
                    _newTasker.sendMessage(_message);
                }/*else { //add_country case
                    NewTasker _newTasker = new NewTasker(_message.getArgs());
                    _newTasker.sendMessage(_message);
                    _newTasker.close();
                    System.out.println("New Queue");
                }*/
            }
            
        }
        
        _worker.close();
        
    }

    private static IMessageEntity splitAndConvertToMessageEntity(String rcvMessage) {
        IMessageEntity _ret;
        String[] _splits = rcvMessage.split(",");
        
        if(_splits.length == 1) {
            _ret = new ControlMessageEntity(_splits, 1); //Constructor for stop case
        }else if(_splits.length == 3) { //TODO: modificar para cuando tenga argumentos
            if(_splits[1].equalsIgnoreCase("")){
                _ret = new ControlMessageEntity(_splits);
            }else{
                _ret = new DataMessageEntity(_splits);
            }
        }else {
            _ret = new DataMessageEntity(_splits);
        }
        
        return _ret;
    }
    
}
