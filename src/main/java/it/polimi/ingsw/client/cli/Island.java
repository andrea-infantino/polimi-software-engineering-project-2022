package it.polimi.ingsw.client.cli;

import java.util.List;

public class Island {
    public int id;
    public boolean hasNoEntryTile;
    public boolean hasMotherNature;
    public List<it.polimi.ingsw.resources.enumerators.Color> students;
    public it.polimi.ingsw.resources.enumerators.TowerColor controlledBy;
    public int islandNumber;

    private String border() {
        if (hasNoEntryTile)
            return Colors.RED + "█";
        if (hasMotherNature)
            return Colors.WITH_MN + "█";
        return Colors.RESET + "█";
    }

    private String printStudents() {
        int[] students = {0, 0, 0, 0, 0};
        for (it.polimi.ingsw.resources.enumerators.Color s : this.students)
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

    private String printTowers() {
        if(controlledBy == null)
            return " FREE   ";
        String team = "";
        switch (controlledBy){
            case WHITE -> team = "WHITE ";
            case BLACK -> team = "BLACK ";
            case GREY -> team = "GREY  ";
        }
        return islandNumber + " " + team;
    }

    @Override
    public String toString(){
        StringBuilder island = new StringBuilder();
        island.append(line1and4());
        island.append("\n");
        island.append(line2());
        island.append("\n");
        island.append(line3());
        island.append("\n");
        island.append(line1and4());
        island.append("\n");

        return island.toString();
    }

    public String line1and4(){
        StringBuilder line = new StringBuilder();
        line.append(Colors.RESET + " ");
        for(int i=0; i<23; i++)
            line.append(border());
        line.append(Colors.RESET + " ");
        return line.toString();
    }

    public String line2(){
        String firstCharacters;
        if(id<10)
            firstCharacters = " Students:  " + id;
        else
            firstCharacters = " Students: " + id;
        return Colors.RESET + border() + Colors.RESET + firstCharacters + "  Towers: " + border() + Colors.RESET;
    }

    public String line3(){
        if(!hasNoEntryTile)
            return Colors.RESET + border() + " " + printStudents() + Colors.RESET + "     " + printTowers() + border() + Colors.RESET;
        else
            return Colors.RESET + border() + " " + printStudents() + Colors.RESET + "  "+ Colors.RED + "!" + Colors.RESET + "  " + printTowers() + border() + Colors.RESET;
    }
}

/*

  █████████████████████████
  █ Students:  0  Towers: █
  █ 0 0 0 0 0     0 GREY  █
  █████████████████████████

 */