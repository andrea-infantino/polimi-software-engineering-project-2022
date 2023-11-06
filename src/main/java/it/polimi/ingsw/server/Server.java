package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.ViewObserver;
import it.polimi.ingsw.server.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    public static final int PORT = 5555;
    public static ViewObserver viewObserver;
    public static AtomicBoolean creatingGame;
    public static Game game;

    public static void main(String[] args) {
        ServerSocket socket;
        creatingGame = new AtomicBoolean(true);
        viewObserver = new ViewObserver();
        try {
            socket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        while (true) {
            try {
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler, "Eryantis_Server - " + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}

