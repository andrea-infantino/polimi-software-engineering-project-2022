package it.polimi.ingsw.resources;

import it.polimi.ingsw.resources.enumerators.MessageCode;

import java.io.Serializable;

public class Message implements Serializable {
    private MessageCode message;
    private Object param1, param2;

    public Message(MessageCode message, Object param1, Object param2) {
        this.message = message;
        this.param1 = param1;
        this.param2 = param2;
    }

    public MessageCode getMessage() {
        return message;
    }

    public Object getParam1() {
        return param1;
    }

    public Object getParam2() {
        return param2;
    }
}
