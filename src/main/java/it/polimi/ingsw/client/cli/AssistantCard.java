package it.polimi.ingsw.client.cli;

public class AssistantCard {
    public int speed;
    public int moves;
    public String playerName;

    public String header(){
        StringBuilder line = new StringBuilder();
        line.append(playerName + ":");
        for(int i = playerName.length() + 1; i<19; i++)
            line.append(" ");
        return line.toString();
    }
    public String line1and4() {
        return "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓";
    }
    public String line2() {
        return "▓ Speed: ▓ Moves: ▓";
    }
    public String line3(){
        if(speed<10)
            return "▓   " + speed + "    ▓   " + moves + "    ▓";
        else
            return "▓   10   ▓   5    ▓";
    }
}

/*
        user1:
        ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
        ▓ Speed: ▓ Moves: ▓
        ▓   0    ▓   0    ▓
        ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
 */
