package it.polimi.ingsw.client.cli;

import java.util.Map;

public class ExpertBoard extends NormalBoard{
    public Map<Integer, Integer> playerCoins;
    public CharacterCard[] characterCards;

    @Override
    public String toString() {
        String toPrint = "\n\n" + separator() + "\nIslands:\n\n" + printIslands() + separator() +
                "\nClouds:\n\n" + printClouds() + "\n" + separator() +
                "\nSchools:\n\n" + printSchools() + "\n" + printPlayerCoins() + "\n" + separator() +
                "\nCharacter Cards:\n\n" + printCharacterCards() + "\n" + separator() + "\n" + printCardsPlayed() + "\n" + separator() +
                "\n" + printDeck() + "\n" + separator();
        return toPrint.replaceAll("\n", "\n     ").replaceAll("     -----", "----------");
    }

    private String printCharacterCards() {
        StringBuilder string = new StringBuilder();
        string.append(characterCards[0].header() + "   " + characterCards[1].header() + "   " + characterCards[2].header() + "\n");
        string.append(characterCards[0].line1() + "   "  + characterCards[1].line1() + "   " + characterCards[2].line1() + "\n");
        string.append(characterCards[0].line2() + "   "  + characterCards[1].line2() + "   " + characterCards[2].line2() + "\n");
        string.append(characterCards[0].line3() + "   "  + characterCards[1].line3() + "   " + characterCards[2].line3() + "\n");
        return string.toString();
    }

    private String printPlayerCoins() {
        StringBuilder coins = new StringBuilder();

        //line 1
        coins.append("   ");
        for(int i=0; i<playerNumber; i++)
            if(i == myId)
                coins.append(Colors.YELLOW + " ▄▄▀▀▄▄           " + Colors.RESET);
            else
                coins.append(" ▄▄▀▀▄▄           ");

        //line 2
        coins.append("\n   ");
        for(int i=0; i<playerNumber; i++)
            if(i == myId)
                coins.append(Colors.YELLOW + "█  " + Colors.RESET + playerCoins.get(i) + "$" + Colors.YELLOW + "  █          " + Colors.RESET);
            else
                coins.append("█  " + playerCoins.get(i) + "$  █          ");

        //line 3
        coins.append("\n   ");
        for(int i=0; i<playerNumber; i++)
            if(i == myId)
                coins.append(Colors.YELLOW + " ▀▀▄▄▀▀           " + Colors.RESET);
            else
                coins.append(" ▀▀▄▄▀▀           ");

        coins.append("\n");

        return coins.toString();
    }
}

/*
   ▄▄▀▀▄▄
  █  5$  █
   ▀▀▄▄▀▀
 */
