package it.polimi.ingsw.client.views;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.enumerators.MessageCode;

import java.util.Scanner;

public class AddToGame extends View{

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);

         do {
            writeLine("Insert name...");
            String name = input.nextLine();
            response = new Message(null, null, null);
            super.getClient().getServerHandler().sendMessage(new Message(MessageCode.ADD_NEW_PLAYER, name, null));
            writeLine("Trying to add the player...");
            while(response.getMessage() == null) { write(""); }  //Loop used to wait the expected message
            if (response.getMessage() == MessageCode.MAX_PLAYER_NUMBER_REACHED) {
                writeLine("Max player number reached, you can't connect to the game now...");
                super.getClient().terminate();
                return;
            }
            if (response.getMessage() == MessageCode.CREATING_GAME)
                writeLine("Game in creation, try later...");
        } while(response.getMessage() != MessageCode.PLAYER_ADDED);

        int playerNumber = (int) response.getParam2();
        writeLine("Player added; waiting for other players...");

        int teamId;
        if((playerNumber) == 4) {
            while(response.getMessage() != MessageCode.PLAYERS_AND_MODE) { write(""); }
            do {
                response = new Message(null, null, null);
                String teamColor = "";
                while (!teamColor.equalsIgnoreCase("WHITE") && !teamColor.equalsIgnoreCase("BLACK")) {
                    writeLine("Insert your team's color (WHITE or BLACK)...");
                    teamColor = input.nextLine();
                    if (!teamColor.equalsIgnoreCase("WHITE") && !teamColor.equalsIgnoreCase("BLACK"))
                        writeLine("Invalid team color...");
                }

                if (teamColor.equalsIgnoreCase("WHITE"))
                    teamId = 0;
                else
                    teamId = 1;
                super.getClient().getServerHandler().sendMessage(new Message(MessageCode.ADD_PLAYER_IN_TEAM, super.getClient().getServerObserver().getInfo().getPlayerId(), teamId));
                while(response.getMessage() == null) { write(""); }  //Loop used to wait the expected message
                if(response.getMessage() == MessageCode.FULL_TEAM)
                    writeLine("The selected team is full...");

            } while(response.getMessage() == MessageCode.FULL_TEAM);
            writeLine("Joined the selected team; waiting for other players...");
        }
        else
            teamId = getClient().getServerObserver().getInfo().getTeamId();

        getClient().getServerObserver().getInfo().setTeamId(teamId);

        clearAll();
    }
}
