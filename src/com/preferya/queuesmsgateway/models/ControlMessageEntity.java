/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.queuesmsgateway.models;

import java.io.Serializable;

/**
 *
 * @author Sergio
 */
public class ControlMessageEntity implements Serializable, IMessageEntity {
    
    private String action;
    private String args;
    private String isoLang;
    
    public ControlMessageEntity() {
        
    }
    
    public ControlMessageEntity(String[] items) {
        this.action = items[0];
        this.args = items[1];
        this.isoLang = items[2];
    }
    
    public ControlMessageEntity(String[] items, int num) {
        this.action = items[0];
    }
    
    public ControlMessageEntity(String action, String args, String iso_lang) {
        this.action = action;
        this.args = args;
        this.isoLang = iso_lang;
    }
    
    public String toString() {
        String _ret = "";
        
        _ret += this.action + ",";
        _ret += this.args;
        
        return _ret;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }
    
    /**
     * @return the args
     */
    public String getArgs() {
        return args;
    }

    @Override
    public String getIsoLang() {
        return isoLang;
    }
    
}
