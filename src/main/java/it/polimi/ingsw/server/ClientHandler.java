package it.polimi.ingsw.server;

import it.polimi.ingsw.resources.ActionPhase1Move;
import it.polimi.ingsw.resources.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import static it.polimi.ingsw.resources.enumerators.MessageCode.*;

public class ClientHandler implements Runnable
{
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    ClientHandler(Socket client)
    {
        this.client = client;
    }

    public Socket getClient() { return client; }

    @Override
    public void run()
    {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        System.out.println("Connected to " + client.getInetAddress());

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException ignored) { }
    }

    private void handleClientConnection() throws IOException {
        try {
            while (true) {
                Object next = input.readObject();
                Message message = (Message) next;
                handleMessage(message);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void sendResponseMessage(Message message) throws IOException
    {
        System.out.println("sending message: " + message.getMessage());
        output.writeObject(message);
    }

    private void handleMessage(Message message) throws IOException {
        switch (message.getMessage()) {
            case START: {
                if(Server.creatingGame.get()) {
                    sendResponseMessage(new Message(ADMIN_CLIENT, null, null));
                    Server.creatingGame.set(false);
                }
                else
                    sendResponseMessage(new Message(GUEST_CLIENT, null, null));
                break;
            }
            case EXPERT_MODE_AND_PLAYER_NUMBER: {
                Message response = Server.viewObserver.expertModeAndPlayerNumber((Boolean) message.getParam1(), (Integer) message.getParam2());
                sendResponseMessage(response);
                break;
            }
            case ADD_NEW_PLAYER: {
                Message response = Server.viewObserver.addNewPlayer((String) message.getParam1(), this);
                sendResponseMessage(response);
                break;
            }
            case ADD_PLAYER_IN_TEAM: {
                Message response = Server.viewObserver.addPlayerInTeam((Integer) message.getParam1(), (Integer) message.getParam2());
                sendResponseMessage(response);
                break;
            }
            case PLAY_ASSISTANT_CARD: {
                Message response = Server.viewObserver.playAssistantCard((Integer) message.getParam1(), (Integer) message.getParam2());
                sendResponseMessage(response);
                break;
            }
            case MOVE_STUDENTS: {
                System.out.println("message received");
                Message response = Server.viewObserver.moveStudents((Integer) message.getParam1(), (List<ActionPhase1Move>) message.getParam2());
                sendResponseMessage(response);
                break;
            }
            case MOVE_MOTHER_NATURE: {
                Message response = Server.viewObserver.moveMotherNature((Integer) message.getParam1(), (Integer) message.getParam2());
                sendResponseMessage(response);
                break;
            }
            case CHOOSE_CLOUD_TILE: {
                Message response = Server.viewObserver.chooseCloudTile((Integer) message.getParam1(), (Integer) message.getParam2());
                sendResponseMessage(response);
                break;
            }
            case EXIT: {
                sendResponseMessage(new Message(ACK, null, null));
                break;
            }
            case PLAY_A_CHARACTER_CARD: {
                Message response = Server.viewObserver.playACharacterCard((Integer) message.getParam1(), (Integer) message.getParam2());
                sendResponseMessage(response);
                break;
            }
            case ACTIVATE_CHARACTER_CARD_EFFECT: {
                Message response = Server.viewObserver.activateCharacterCardEffect(message.getParam1(), message.getParam2());
                sendResponseMessage(response);
                break;
            }
        }
    }

}
