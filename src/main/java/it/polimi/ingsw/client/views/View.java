package it.polimi.ingsw.client.views;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.resources.Message;

public abstract class View implements Runnable{
    private boolean stop;
    private Client client;
    public Message response;

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public synchronized boolean mustStop() { return stop; }
    public synchronized void stop() {
        stop = true;
        notifyAll();
    }
    public static void writeLine(String string) {
        System.out.println(string);
    }
    public static void write(String string) {
        System.out.print(string);
    }
    public static void clearAll() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
