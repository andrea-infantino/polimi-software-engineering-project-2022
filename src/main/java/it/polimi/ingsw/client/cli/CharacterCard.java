package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.resources.enumerators.Color;

import java.util.List;

public class CharacterCard {
    public int id;
    public int number;
    public int cost;
    public int noEntryTiles;
    public List<Color> students;

    private String description;

    private String getName() {
        switch (id) {
            case 0: return "    Monk    "; //tested
            case 1: return "   Farmer   "; //tested
            case 2: return "   Herald   "; //tested
            case 3: return "  Postman   "; //tested
            case 4: return "Grandmother "; //tested
            case 5: return "  Centaur   "; //tested
            case 6: return "   Jester   "; //tested
            case 7: return "   Knight   "; //tested
            case 8: return "Mushroom-Man"; //tested
            case 9: return "  Minstrel  "; //tested
            case 10: return "  Princess  "; //tested
            case 11: return "   Thief    "; //tested
        }
        return null;
    }

    private String printStudents() {
        StringBuilder string = new StringBuilder();
        for(Color c : students)
            switch (c) {
            case RED -> string.append(Colors.RED + "¶ ");
            case BLUE -> string.append(Colors.BLUE + "¶ ");
            case PINK -> string.append(Colors.PURPLE + "¶ ");
            case GREEN -> string.append(Colors.GREEN + "¶ ");
            case YELLOW -> string.append(Colors.YELLOW + "¶ ");
            }
        string.append(Colors.RESET);
        return string.toString();
    }

    public String header() {
        StringBuilder string = new StringBuilder();
        int number = this.number +1;
        if(id != 4)
            if( id == 0 || id == 10 || id == 6)
                string.append("Card " + number + ":  " + printStudents());
            else
                string.append("Card " + number + ":              ");
        else {
            string.append("Card " + number +":  ");
            for(int i=0; i<4; i++)
                if(i<noEntryTiles)
                    string.append(Colors.RED + "! ");
                else
                    string.append("  ");
            string.append("    ");
            string.append(Colors.RESET);
        }

        if(id == 0 || id == 10)
            string.append("    ");
        return string.toString();
    }

    public String line1() {
        return "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄";
    }

    public String line2() {
       return "█ " + getName() + " █ " + cost + "$ █";
    }

    public String line3() {
        return "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀";
    }
}

/*

        Card 1:  ! ! ! !
        ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
        █ Mushroom-man █ 5$ █
        ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀

 */

