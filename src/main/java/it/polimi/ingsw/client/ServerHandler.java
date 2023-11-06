package it.polimi.ingsw.client;

import it.polimi.ingsw.client.views.View;
import it.polimi.ingsw.resources.Message;
import static it.polimi.ingsw.resources.enumerators.MessageCode.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerHandler implements Runnable
{
    private Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Client client;
    private AtomicBoolean mustStop;

    ServerHandler(Socket server, Client client)
    {
        this.server = server;
        this.client = client;
        this.mustStop = new AtomicBoolean(false);
    }

    @Override
    public void run()
    {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            View.writeLine("can't connect to " + server.getInetAddress());
            if(!Client.isGuiMode)
                client.terminate();
            return;
        }

        try {
            handleClientConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            server.close();
        } catch (IOException ignored) { }
        if(!Client.isGuiMode)
            client.terminate();
    }

    private void handleClientConnection() throws IOException
    {
        try {
            sendMessage(new Message(START, null, null));
            boolean stop = false;
            while (!stop) {
                try {
                    Object next = input.readObject();
                    if (next != null) {
                        Message message = (Message) next;
                        handleMessage(message);
                    }
                } catch (IOException e) {
                    if (mustStop.get()) {
                        stop = true;
                    } else {
                        throw e;
                    }
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            View.writeLine("invalid stream from server");
        }
    }

    private void handleMessage(Message message) throws IOException {
        switch (message.getMessage()) {
            case ACK -> client.getServerObserver().ack();
            case ADMIN_CLIENT -> client.getServerObserver().adminClient();
            case GUEST_CLIENT -> client.getServerObserver().guestClient();
            case CREATING_GAME -> client.getServerObserver().creatingGame();
            case MAX_PLAYER_NUMBER_REACHED -> client.getServerObserver().maxPlayerNumberReached();
            case GAME_CREATED -> client.getServerObserver().gameCreated();
            case PLAYER_ADDED -> client.getServerObserver().playerAdded(message);
            case FULL_TEAM -> client.getServerObserver().fullTeam();
            case NOT_YOUR_TURN -> client.getServerObserver().notYourTurn();
            case UNPLAYABLE_ASSISTANT_CARD -> client.getServerObserver().unplayableAssistantCard();
            case INVALID_MOVE -> client.getServerObserver().invalidMove();
            case PLAYERS_AND_MODE -> client.getServerObserver().playersAndMode(message);
            case START_GAME_SESSION -> client.getServerObserver().startGameSession(message);
            case MODEL_CHANGED -> client.getServerObserver().modelChanged(message);
            case START_PLANNING_PHASE -> client.getServerObserver().startPlanningPhase();
            case NEW_TURN -> client.getServerObserver().newTurn(message);
            case START_ACTION_PHASE_1 -> client.getServerObserver().startActionPhase1();
            case START_ACTION_PHASE_2 -> client.getServerObserver().startActionPhase2();
            case START_ACTION_PHASE_3 -> client.getServerObserver().startActionPhase3();
            case END_GAME -> client.getServerObserver().endGame(message);
            case PLAYER_USED_CHARACTER_CARD -> client.getServerObserver().playerUsedCharacterCard((Integer) message.getParam1(), (Integer) message.getParam2());
            case NOT_ENOUGH_COINS -> client.getServerObserver().notEnoughCoins();
            case ALREADY_PLAYED_A_CHARACTER_CARD -> client.getServerObserver().alreadyPlayedACharacterCard();
            case WAIT_FOR_CHARACTER_CARD_PARAMETERS -> client.getServerObserver().waitForCharacterCardParameters();

            default -> View.writeLine("");
        }
    }

    public Client getClient()
    {
        return client;
    }

    public void sendMessage(Message message)
    {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            if(!Client.isGuiMode)
                client.terminate();
        }
    }

    public void stop()
    {
        mustStop.set(true);
        try {
            server.shutdownInput();
        } catch (IOException ignored) { }
    }
}
