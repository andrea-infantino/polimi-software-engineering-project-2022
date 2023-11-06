package it.polimi.ingsw.client.views;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.gui.App;
import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.enumerators.MessageCode;
import javafx.application.Platform;

import java.util.Scanner;

public class GameSetup extends View{
    @Override
    public void run() {

        Scanner input = new Scanner(System.in);

        int playerNumber = -1;
        boolean expertMode = false;

        while (playerNumber < 0 || playerNumber > 4) {
            writeLine("Insert the number of players...");
            playerNumber = Integer.parseInt(input.nextLine());
            if (playerNumber < 0 || playerNumber > 4)
                writeLine("Invalid number of players.");
        }

        String mode = "";
        while (!mode.equalsIgnoreCase("expert") && !mode.equalsIgnoreCase("normal")) {
            writeLine("Insert 'Expert' if you want start an expert game session, 'Normal' if you want to start a normal game session...");
            mode = input.nextLine();
            if (!mode.equalsIgnoreCase("expert") && !mode.equalsIgnoreCase("normal"))
                writeLine("Invalid game mode.");
        }
        expertMode = mode.equalsIgnoreCase("expert");


    response = new Message(null, null, null);

    super.getClient().getServerHandler().sendMessage(new Message(MessageCode.EXPERT_MODE_AND_PLAYER_NUMBER, expertMode, playerNumber));
    writeLine("Creating game...");
    while(response.getMessage() != MessageCode.GAME_CREATED) { write(""); }  //Loop used to wait the expected message
    writeLine("Game created successfully");

    getClient().changeView(new AddToGame());
    clearAll();
    }

}
