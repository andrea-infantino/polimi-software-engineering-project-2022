package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.TowerColor;

import java.util.List;
import java.util.Map;

public class School {
    public List<Color> entrance;
    public Map<Color,Integer> diningRoom;
    public Map<Color, Boolean> professors;
    public int towers;
    public TowerColor team;
    public int playerId;
    public String playerName;
    public boolean yours;

    public String header() {
        StringBuilder line = new StringBuilder();
        if(playerName.length() <= 6) {
            line.append(playerName);
            for (int i = playerName.length(); i < 6; i++)
                line.append(" ");
        }
        else
            for(int i = 0; i<6; i++)
                line.append(playerName.charAt(i));
        line.append(" -  ");
        switch (team) {
            case WHITE -> line.append("WHITE");
            case BLACK -> line.append("BLACK");
            case GREY -> line.append("GREY ");
        }
        return line.toString();
    }

    private String border(){
        if (yours)
            return Colors.SELECTED + "/";
        else
            return Colors.WITH_MN + "/";
    }

    private String printHall(){
        return Colors.GREEN + "" + diningRoom.get(Color.GREEN) + " " +
                Colors.RED + "" + diningRoom.get(Color.RED) + " " +
                Colors.YELLOW + "" + diningRoom.get(Color.YELLOW) + " " +
                Colors.PURPLE + "" + diningRoom.get(Color.PINK) + " " +
                Colors.BLUE + "" + diningRoom.get(Color.BLUE);
    }

    private String printEntrance(){
        int[] students = {0, 0, 0, 0, 0};
        for (Color s : this.entrance)
            switch(s) {
                case GREEN -> students[0]++;
                case RED -> students[1]++;
                case YELLOW -> students[2]++;
                case PINK -> students[3]++;
                case BLUE -> students[4]++;
            }
        return Colors.GREEN + "" + students[0] + " " +
                Colors.RED + "" + students[1] + " " +
                Colors.YELLOW + "" + students[2] + " " +
                Colors.PURPLE + "" + students[3] + " " +
                Colors.BLUE + "" + students[4];
    }

    public String printProfessors(){
        StringBuilder string = new StringBuilder();
        if(professors.get(Color.GREEN))
            string.append(Colors.GREEN + "¶ ");
        else
            string.append("  ");
        if(professors.get(Color.RED))
            string.append(Colors.RED + "¶ ");
        else
            string.append("  ");
        if(professors.get(Color.YELLOW))
            string.append(Colors.YELLOW + "¶ ");
        else
            string.append("  ");
        if(professors.get(Color.PINK))
            string.append(Colors.PURPLE + "¶ ");
        else
            string.append("  ");
        if(professors.get(Color.BLUE))
            string.append(Colors.BLUE + "¶");
        else
            string.append(" ");
        return string.toString();
    }

    @Override
    public String toString(){
        return header() + "\n" +
                line1and13() + "\n" +
                line2() + "\n" +
                line3() + "\n" +
                line4and7and10() + "\n" +
                line5() + "\n" +
                line6() + "\n" +
                line4and7and10() + "\n" +
                line8() + "\n" +
                line9() + "\n" +
                line4and7and10() + "\n" +
                line11() + "\n" +
                line12() + "\n" +
                line1and13() + "\n";
    }

    public String line1and13(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i<15; i++)
            s.append(border());
        s.append(Colors.RESET);
        return s.toString();
    }
    public String line2(){
        return border() + border() + Colors.RESET + "  Towers:  " + border() + border();
    }
    public String line3(){
        return border() + border() + Colors.RESET + "     " + towers + "     " + border() + border();
    }
    public String line4and7and10(){
        return border()+border()+"-----------"+border()+border();
    }
    public String line5(){
        return border() + border() + Colors.RESET + "Professors:" + border() + border();
    }
    public String line6(){
        return border() + border() + " " + printProfessors() + " " + border() + border();
    }
    public String line8(){
        return border() + border() + Colors.RESET + "   Hall:   " + border() + border();
    }
    public String line9() {
        return border() + border() + " " + printHall() + " " + border() + border();
    }
    public String line11() {
        return border() + border() + Colors.RESET + " Entrance: " + border() + border();
    }
    public String line12() {
        return border() + border() + " " + printEntrance() + " " + border() + border();
    }
}

/*
        Name1  -  WHITE
        ///////////////
        //  Towers:  //
        //     7     //
        //-----------//
        //Professors://
        // ¶ ¶ ¶ ¶ ¶ //
        //-----------//
        //   Hall:   //
        // 0 0 0 0 0 //
        //-----------//
        // Entrance: //
        // 0 0 0 0 0 //
        ///////////////
 */
