package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.*;
import it.polimi.ingsw.client.gui.App;
import it.polimi.ingsw.client.gui.ConnectionScreen;
import it.polimi.ingsw.client.views.LoadingView;
import it.polimi.ingsw.client.views.View;
import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.MessageCode;
import it.polimi.ingsw.resources.enumerators.ScreenSwitch;
import it.polimi.ingsw.resources.enumerators.TowerColor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Client implements Runnable
{
    private ServerHandler serverHandler;
    private ServerObserver serverObserver;
    private boolean mustStop;

    public static String ip;
    public static int port;

    //Exclusive attributes for CLI
    private View nextView;
    private View currentView;

    //Exclusive attributes for GUI
    public static boolean isGuiMode;
    public ScreenSwitch preLoadingScreenSwitch;
    public ScreenSwitch loadingScreenSwitch;
    public static IntegerProperty connectionIndex; // 0 = in connection, 1 = connected, -1 = can't connect


    public static void main(String[] args)
    {
        if(isGuiMode) {
            connectionIndex = new SimpleIntegerProperty(0);
            App.main(args);
        }
        else {
            Client client = new Client();
            client.run();
        }
    }

    @Override
    public void run()
    {
        if(!isGuiMode) {
            Scanner input = new Scanner(System.in);

            View.writeLine("Press Enter to start...");
            input.nextLine();

            View.writeLine("Insert ip-address...");
            ip = input.nextLine();
            View.writeLine("Insert port...");
            try {
                port = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                View.writeLine("Can't connect to the server");
                return;
            }
            View.clearAll();
        }

        Socket server;
        try {
            server = new Socket(ip, port);
        } catch (IOException e) {
            if(isGuiMode) {
                connectionIndex.set(-1);
                connectionIndex.set(0);
            }
            else
                View.writeLine("Can't connect to the server");
            return;
        }

        mustStop = false;
        serverHandler = new ServerHandler(server, this);
        Thread thread = new Thread(serverHandler, "Eryantis_Server - " + server.getInetAddress().getHostAddress());
        thread.start();
        serverObserver = new ServerObserver(this);
        if(!isGuiMode) {
            startCLI();
            serverHandler.stop();
        }
        else
            connectionIndex.set(1);
    }

    public ServerHandler getServerHandler()
    {
        return serverHandler;
    }

    public ServerObserver getServerObserver() {
        return serverObserver;
    }



    //Exclusive methods for CLI
    private void startCLI()
    {
        boolean stop;

        synchronized (this) {
            stop = mustStop;
        }
        while (!stop) {
            if (currentView == null) {
                currentView = new LoadingView();
            }

            currentView.setClient(this);
            currentView.run();

            synchronized (this) {
                stop = mustStop;
                currentView = nextView;
                nextView = null;
            }
        }
    }

    public View getCurrentView() { return currentView; }

    public synchronized void changeView(View newView)
    {
        this.nextView = newView;
        currentView.stop();
    }

    public synchronized void terminate()
    {
        if (!mustStop) {
            mustStop = true;
            currentView.stop();
        }
    }

   /* private static void testCLI(){
        ExpertBoard board = new ExpertBoard();
        board.islands = new ArrayList<>();
        for (int i = 0; i<12; i++) {
            Island island = new Island();
            island.id = i;
            island.islandNumber = (int) Math.ceil(i/2);
            island.controlledBy = TowerColor.WHITE;
            island.hasMotherNature = false;
            if(i == 1)
                island.hasNoEntryTile = true;
            else
                island.hasNoEntryTile = false;
            island.students = new ArrayList<>();
            island.students.add(Color.RED);
            island.students.add(Color.RED);
            island.students.add(Color.RED);
            island.students.add(Color.YELLOW);
            island.students.add(Color.YELLOW);
            island.students.add(Color.PINK);
            board.islands.add(island);
        }
        int n = 4;
        board.clouds = new ArrayList<>();
        board.schools = new ArrayList<>();
        board.playerNumber = n;
        for(int i = 0; i<n; i++) {
            Cloud cloud = new Cloud();
            cloud.id = i;
            cloud.students = new ArrayList<>();
            cloud.students.add(Color.RED);
            cloud.students.add(Color.BLUE);
            cloud.students.add(Color.PINK);
            cloud.playerNumber = n;
            board.clouds.add(cloud);

            School school = new School();
            if(i==0)
                school.yours = true;
            school.entrance = new ArrayList<>();
            school.entrance.add(Color.RED);
            school.entrance.add(Color.RED);
            school.entrance.add(Color.RED);
            school.entrance.add(Color.PINK);
            school.entrance.add(Color.BLUE);
            school.entrance.add(Color.GREEN);
            school.towers = 6;
            school.team = TowerColor.WHITE;
            school.playerName = "user" + i;
            school.playerId = i;
            school.diningRoom = new HashMap<>();
            school.diningRoom.put(Color.RED, 3);
            school.diningRoom.put(Color.BLUE, 3);
            school.diningRoom.put(Color.GREEN, 3);
            school.diningRoom.put(Color.YELLOW, 3);
            school.diningRoom.put(Color.PINK, 3);
            school.professors = new HashMap<>();
            school.professors.put(Color.RED, true);
            school.professors.put(Color.BLUE, true);
            school.professors.put(Color.GREEN, false);
            school.professors.put(Color.YELLOW, true);
            school.professors.put(Color.PINK, false);
            board.schools.add(school);
        }

        board.cardsPlayed = new ArrayList<>();
        for(int i=0; i < 4; i++){
            AssistantCard a = new AssistantCard();
            a.moves = 5;
            a.speed = 9;
            a.playerName = "user" + i;
            board.cardsPlayed.add(a);
        }

        board.deck = new ArrayList<>();
        for(int i=0; i < 10; i++){
            AssistantCard a = new AssistantCard();
            a.moves = (int) Math.ceil((i+1)/2);
            a.speed = i+1;
            a.playerName = "user" + i;
            board.deck.add(a);
        }

        board.playerCoins = new HashMap<>();
        for(int i=0; i<n; i++)
            board.playerCoins.put(i, i);

        board.characterCards = new CharacterCard[3];
        board.characterCards[0] = new CharacterCard();
        board.characterCards[0].id = 4;
        board.characterCards[0].noEntryTiles = 0;
        board.characterCards[0].cost = 1;
        board.characterCards[0].number = 0;
        board.characterCards[1] = new CharacterCard();
        board.characterCards[1].id = 6;
        Color[] s = {Color.RED, Color.BLUE, Color.PINK, Color.PINK, Color.YELLOW, Color.GREEN};
        board.characterCards[1].students = new ArrayList<>();
        for(Color c : s)
            board.characterCards[1].students.add(c);
        board.characterCards[1].cost = 2;
        board.characterCards[1].number = 1;
        board.characterCards[2] = new CharacterCard();
        board.characterCards[2].id = 1;
        board.characterCards[2].cost = 3;
        board.characterCards[2].number = 3;

        System.out.println(board);
    }*/

}
