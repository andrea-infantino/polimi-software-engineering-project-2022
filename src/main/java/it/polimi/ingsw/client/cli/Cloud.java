package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.resources.enumerators.Color;

import javax.swing.*;
import java.util.List;

public class Cloud {
    public int id;
    public boolean selected;
    public List<Color> students;
    public int playerNumber;

    private String border(){
        if(selected)
            return Colors.SELECTED + "░";
        else
            return Colors.RESET + "░";
    }
    @Override
    public String toString() {
        StringBuilder cloud = new StringBuilder();
        cloud.append(line1() + "\n");
        cloud.append(line2() + "\n");
        cloud.append(line3() + "\n");
        cloud.append(line4() + "\n");
        return cloud.toString();
    }

    public String line1(){
        StringBuilder line = new StringBuilder();
        line.append(Colors.RESET + "   ");
        for(int i = 0; i<7; i++)
            line.append(border());
        line.append(Colors.RESET + "   ");

        return line.toString();
    }

    public String line2(){
        StringBuilder line = new StringBuilder();
        line.append(Colors.RESET + " " + border() + border() + " ");
        if(!students.isEmpty())
            line.append(getColor(students.get(0)) + "¶" );
        else
            line.append(" ");
        line.append("   ");
        if(!students.isEmpty())
            line.append(getColor(students.get(1)) + "¶" );
        else
            line.append(" ");
        line.append(Colors.RESET + " " + border() + border() + " ");

        return line.toString();
    }

    public String line3() {
        if(students.isEmpty())
            return border() + "           " + border();
        StringBuilder line = new StringBuilder();
        line.append(border() + " ");
        if(playerNumber == 3)
            line.append(getColor(students.get(2)) + "¶       " + getColor(students.get(3)) + "¶");
        else
            line.append("    " + getColor(students.get(2)) + "¶    ");
        line.append(" " + border());
        return line.toString();
    }

    public String line4() {
        StringBuilder line = new StringBuilder();
        line.append(" ");
        for(int i = 0; i<4; i++)
            line.append(border());
        line.append(" " + id + " ");
        for(int i = 0; i<4; i++)
            line.append(border());
        line.append(" ");
        return line.toString();
    }

    private String getColor(Color c) {
        switch (c){
            case RED -> { return "" + Colors.RED; }
            case GREEN -> {return "" + Colors.GREEN;}
            case YELLOW -> {return "" + Colors.YELLOW;}
            case PINK -> { return "" + Colors.PURPLE;}
            case BLUE -> {return "" + Colors.BLUE;}
        }
        return null;
    }
}

/*
           ░░░░░░░
         ░░ ¶   ¶ ░░
        ░ ¶       ¶ ░
         ░░░░ 1 ░░░░

 */