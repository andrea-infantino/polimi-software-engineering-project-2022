package it.polimi.ingsw.client.cli;

import java.util.List;

public class NormalBoard {
    public int myId;
    public int playerNumber;
    public List<Island> islands;
    public List<Cloud> clouds;
    public List<School> schools;
    public List<AssistantCard> cardsPlayed;
    public List<AssistantCard> deck;

    public String printSchools(){
        StringBuilder string = new StringBuilder();
        switch (playerNumber) {
            case 4 -> {
                string.append(
                        schools.get(0).header() + "   " +
                        schools.get(1).header() + "   " +
                        schools.get(2).header() + "   " +
                        schools.get(3).header() + "\n");
                string.append(
                        schools.get(0).line1and13() + "   " +
                        schools.get(1).line1and13() + "   " +
                        schools.get(2).line1and13() + "   " +
                        schools.get(3).line1and13() + "\n");
                string.append(
                        schools.get(0).line2() + "   " +
                        schools.get(1).line2() + "   " +
                        schools.get(2).line2() + "   " +
                        schools.get(3).line2() + "\n");
                string.append(
                        schools.get(0).line3() + "   " +
                        schools.get(1).line3() + "   " +
                        schools.get(2).line3() + "   " +
                        schools.get(3).line3() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                        schools.get(1).line4and7and10() + "   " +
                        schools.get(2).line4and7and10() + "   " +
                        schools.get(3).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line5() + "   " +
                        schools.get(1).line5() + "   " +
                        schools.get(2).line5() + "   " +
                        schools.get(3).line5() + "\n");
                string.append(
                        schools.get(0).line6() + "   " +
                        schools.get(1).line6() + "   " +
                        schools.get(2).line6() + "   " +
                        schools.get(3).line6() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                        schools.get(1).line4and7and10() + "   " +
                        schools.get(2).line4and7and10() + "   " +
                        schools.get(3).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line8() + "   " +
                        schools.get(1).line8() + "   " +
                        schools.get(2).line8() + "   " +
                        schools.get(3).line8() + "\n");
                string.append(
                        schools.get(0).line9() + "   " +
                        schools.get(1).line9() + "   " +
                        schools.get(2).line9() + "   " +
                        schools.get(3).line9() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                        schools.get(1).line4and7and10() + "   " +
                        schools.get(2).line4and7and10() + "   " +
                        schools.get(3).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line11() + "   " +
                        schools.get(1).line11() + "   " +
                        schools.get(2).line11() + "   " +
                        schools.get(3).line11() + "\n");
                string.append(
                        schools.get(0).line12() + "   " +
                        schools.get(1).line12() + "   " +
                        schools.get(2).line12() + "   " +
                        schools.get(3).line12() + "\n");
                string.append(
                        schools.get(0).line1and13() + "   " +
                        schools.get(1).line1and13() + "   " +
                        schools.get(2).line1and13() + "   " +
                        schools.get(3).line1and13() + "\n");
            }
            case 3 -> {
                string.append(
                        schools.get(0).header() + "   " +
                                schools.get(1).header() + "   " +
                                schools.get(2).header() + "\n");
                string.append(
                        schools.get(0).line1and13() + "   " +
                                schools.get(1).line1and13() + "   " +
                                schools.get(2).line1and13() + "\n");
                string.append(
                        schools.get(0).line2() + "   " +
                                schools.get(1).line2() + "   " +
                                schools.get(2).line2() + "\n");
                string.append(
                        schools.get(0).line3() + "   " +
                                schools.get(1).line3() + "   " +
                                schools.get(2).line3() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                                schools.get(1).line4and7and10() + "   " +
                                schools.get(2).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line5() + "   " +
                                schools.get(1).line5() + "   " +
                                schools.get(2).line5() + "\n");
                string.append(
                        schools.get(0).line6() + "   " +
                                schools.get(1).line6() + "   " +
                                schools.get(2).line6() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                                schools.get(1).line4and7and10() + "   " +
                                schools.get(2).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line8() + "   " +
                                schools.get(1).line8() + "   " +
                                schools.get(2).line8() + "\n");
                string.append(
                        schools.get(0).line9() + "   " +
                                schools.get(1).line9() + "   " +
                                schools.get(2).line9() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                                schools.get(1).line4and7and10() + "   " +
                                schools.get(2).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line11() + "   " +
                                schools.get(1).line11() + "   " +
                                schools.get(2).line11() + "\n");
                string.append(
                        schools.get(0).line12() + "   " +
                                schools.get(1).line12() + "   " +
                                schools.get(2).line12() + "\n");
                string.append(
                        schools.get(0).line1and13() + "   " +
                                schools.get(1).line1and13() + "   " +
                                schools.get(2).line1and13() + "\n");
            }
            case 2 -> {
                string.append(
                        schools.get(0).header() + "   " +
                                schools.get(1).header() + "\n");
                string.append(
                        schools.get(0).line1and13() + "   " +
                                schools.get(1).line1and13() + "\n");
                string.append(
                        schools.get(0).line2() + "   " +
                                schools.get(1).line2() + "\n");
                string.append(
                        schools.get(0).line3() + "   " +
                                schools.get(1).line3() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                                schools.get(1).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line5() + "   " +
                                schools.get(1).line5() + "\n");
                string.append(
                        schools.get(0).line6() + "   " +
                                schools.get(1).line6() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                                schools.get(1).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line8() + "   " +
                                schools.get(1).line8() + "\n");
                string.append(
                        schools.get(0).line9() + "   " +
                                schools.get(1).line9() + "\n");
                string.append(
                        schools.get(0).line4and7and10() + "   " +
                                schools.get(1).line4and7and10() + "\n");
                string.append(
                        schools.get(0).line11() + "   " +
                                schools.get(1).line11() + "\n");
                string.append(
                        schools.get(0).line12() + "   " +
                                schools.get(1).line12() + "\n");
                string.append(
                        schools.get(0).line1and13() + "   " +
                                schools.get(1).line1and13() + "\n");
            }
        }
        return string.toString();
    }

    public String printClouds(){
        StringBuilder string = new StringBuilder();
        switch (playerNumber) {
            case 4 -> {
                string.append(getCloudById(0).line1()+ "   " +
                        getCloudById(1).line1() + "   " +
                        getCloudById(2).line1() + "   " +
                        getCloudById(3).line1() + "\n");

                string.append(getCloudById(0).line2()+ "   " +
                        getCloudById(1).line2() + "   " +
                        getCloudById(2).line2() + "   " +
                        getCloudById(3).line2() + "\n");

                string.append(getCloudById(0).line3()+ "   " +
                        getCloudById(1).line3() + "   " +
                        getCloudById(2).line3() + "   " +
                        getCloudById(3).line3() + "\n");

                string.append(getCloudById(0).line4()+ "   " +
                        getCloudById(1).line4() + "   " +
                        getCloudById(2).line4() + "   " +
                        getCloudById(3).line4() + "\n");
            }

            case 3 -> {
                string.append(getCloudById(0).line1()+ "   " +
                        getCloudById(1).line1() + "   " +
                        getCloudById(2).line1() + "\n");

                string.append(getCloudById(0).line2()+ "   " +
                        getCloudById(1).line2() + "   " +
                        getCloudById(2).line2() + "\n");

                string.append(getCloudById(0).line3()+ "   " +
                        getCloudById(1).line3() + "   " +
                        getCloudById(2).line3() + "\n");

                string.append(getCloudById(0).line4()+ "   " +
                        getCloudById(1).line4() + "   " +
                        getCloudById(2).line4() + "\n");
            }

            case 2 -> {
                string.append(getCloudById(0).line1()+ "   " +
                        getCloudById(1).line1() + "\n");

                string.append(getCloudById(0).line2()+ "   " +
                        getCloudById(1).line2() + "\n");

                string.append(getCloudById(0).line3()+ "   " +
                        getCloudById(1).line3() + "\n");

                string.append(getCloudById(0).line4()+ "   " +
                        getCloudById(1).line4() + "\n");
            }
        }

        return string.toString();
    }

    public String printIslands() {
        StringBuilder board = new StringBuilder();
        switch (islands.size()) {
            case 3 -> {
                board.append("              " + getIslandById(0).line1and4() + "\n");
                board.append("              " + getIslandById(0).line2() + "\n");
                board.append("              " + getIslandById(0).line3() + "\n");
                board.append("              " + getIslandById(0).line1and4() + "\n\n");

                board.append(getIslandById(1).line1and4() + "   " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(1).line2() + "   " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(1).line3() + "   " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(1).line1and4() + "   " + getIslandById(2).line1and4() + "\n\n");
            }

            case 4 -> {
                board.append(getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "\n");
                board.append(getIslandById(0).line2() + "   " + getIslandById(1).line2() + "\n");
                board.append(getIslandById(0).line3() + "   " + getIslandById(1).line3() + "\n");
                board.append(getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "\n\n");

                board.append(getIslandById(3).line1and4() + "   " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(3).line2() + "   " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(3).line3() + "   " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(3).line1and4() + "   " + getIslandById(2).line1and4() + "\n\n");
            }

            case 5 -> {
                board.append("              " + getIslandById(0).line1and4() + "\n");
                board.append("              " + getIslandById(0).line2() + "\n");
                board.append("              " + getIslandById(0).line3() + "\n");
                board.append("              " + getIslandById(0).line1and4() + "\n\n");

                board.append(getIslandById(4).line1and4() + "   " + getIslandById(1).line1and4() + "\n");
                board.append(getIslandById(4).line2() + "   " + getIslandById(1).line2() + "\n");
                board.append(getIslandById(4).line3() + "   " + getIslandById(1).line3() + "\n");
                board.append(getIslandById(4).line1and4() + "   " + getIslandById(1).line1and4() + "\n\n");

                board.append(getIslandById(3).line1and4() + "   " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(3).line2() + "   " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(3).line3() + "   " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(3).line1and4() + "   " + getIslandById(2).line1and4() + "\n\n");
            }

            case 6 -> {
                board.append("              " + getIslandById(0).line1and4() + "\n");
                board.append("              " + getIslandById(0).line2() + "\n");
                board.append("              " + getIslandById(0).line3() + "\n");
                board.append("              " + getIslandById(0).line1and4() + "\n\n");

                board.append(getIslandById(5).line1and4() + "   " + getIslandById(1).line1and4() + "\n");
                board.append(getIslandById(5).line2() + "   " + getIslandById(1).line2() + "\n");
                board.append(getIslandById(5).line3() + "   " + getIslandById(1).line3() + "\n");
                board.append(getIslandById(5).line1and4() + "   " + getIslandById(1).line1and4() + "\n\n");

                board.append(getIslandById(4).line1and4() + "   " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(4).line2() + "   " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(4).line3() + "   " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(4).line1and4() + "   " + getIslandById(2).line1and4() + "\n\n");

                board.append("              " + getIslandById(3).line1and4() + "\n");
                board.append("              " + getIslandById(3).line2() + "\n");
                board.append("              " + getIslandById(3).line3() + "\n");
                board.append("              " + getIslandById(3).line1and4() + "\n\n");
            }

            case 7 -> {
                board.append("              " + getIslandById(0).line1and4() + "\n");
                board.append("              " + getIslandById(0).line2() + "\n");
                board.append("              " + getIslandById(0).line3() + "\n");
                board.append("              " + getIslandById(0).line1and4() + "\n\n");

                board.append(getIslandById(6).line1and4() + "   " + getIslandById(1).line1and4() + "\n");
                board.append(getIslandById(6).line2() + "   " + getIslandById(1).line2() + "\n");
                board.append(getIslandById(6).line3() + "   " + getIslandById(1).line3() + "\n");
                board.append(getIslandById(6).line1and4() + "   " + getIslandById(1).line1and4() + "\n\n");

                board.append(getIslandById(5).line1and4() + "   " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(5).line2() + "   " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(5).line3() + "   " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(5).line1and4() + "   " + getIslandById(2).line1and4() + "\n\n");

                board.append(getIslandById(4).line1and4() + "   " + getIslandById(3).line1and4() + "\n");
                board.append(getIslandById(4).line2() + "   " + getIslandById(3).line2() + "\n");
                board.append(getIslandById(4).line3() + "   " + getIslandById(3).line3() + "\n");
                board.append(getIslandById(4).line1and4() + "   " + getIslandById(3).line1and4() + "\n\n");
            }

            case 8 -> {
                board.append(getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "   " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(0).line2() + "   " + getIslandById(1).line2() + "   " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(0).line3() + "   " + getIslandById(1).line3() + "   " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "   " + getIslandById(2).line1and4() + "\n\n");

                board.append(getIslandById(7).line1and4() + "                               " + getIslandById(3).line1and4() + "\n");
                board.append(getIslandById(7).line2() + "                               " + getIslandById(3).line2() + "\n");
                board.append(getIslandById(7).line3() + "                               " + getIslandById(3).line3() + "\n");
                board.append(getIslandById(7).line1and4() + "                               " + getIslandById(3).line1and4() + "\n\n");

                board.append(getIslandById(6).line1and4() + "   " + getIslandById(5).line1and4() + "   " + getIslandById(4).line1and4() + "\n");
                board.append(getIslandById(6).line2() + "   " + getIslandById(5).line2() + "   " + getIslandById(4).line2() + "\n");
                board.append(getIslandById(6).line3() + "   " + getIslandById(5).line3() + "   " + getIslandById(4).line3() + "\n");
                board.append(getIslandById(6).line1and4() + "   " + getIslandById(5).line1and4() + "   " + getIslandById(4).line1and4() + "\n\n");
            }

            case 9 -> {
                board.append("              " + getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "\n");
                board.append("              " + getIslandById(0).line2() + "   " + getIslandById(1).line2() + "\n");
                board.append("              " + getIslandById(0).line3() + "   " + getIslandById(1).line3() + "\n");
                board.append("              " + getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "\n\n");

                board.append(getIslandById(8).line1and4() + "                               " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(8).line2() + "                               " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(8).line3() + "                               " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(8).line1and4() + "                               " + getIslandById(2).line1and4() + "\n\n");

                board.append(getIslandById(7).line1and4() + "                               " + getIslandById(3).line1and4() + "\n");
                board.append(getIslandById(7).line2() + "                               " + getIslandById(3).line2() + "\n");
                board.append(getIslandById(7).line3() + "                               " + getIslandById(3).line3() + "\n");
                board.append(getIslandById(7).line1and4() + "                               " + getIslandById(3).line1and4() + "\n\n");

                board.append(getIslandById(6).line1and4() + "   " + getIslandById(5).line1and4() + "   " + getIslandById(4).line1and4() + "\n");
                board.append(getIslandById(6).line2() + "   " + getIslandById(5).line2() + "   " + getIslandById(4).line2() + "\n");
                board.append(getIslandById(6).line3() + "   " + getIslandById(5).line3() + "   " + getIslandById(4).line3() + "\n");
                board.append(getIslandById(6).line1and4() + "   " + getIslandById(5).line1and4() + "   " + getIslandById(4).line1and4() + "\n\n");
            }

            case 10 -> {
                board.append("              " + getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "\n");
                board.append("              " + getIslandById(0).line2() + "   " + getIslandById(1).line2() + "\n");
                board.append("              " + getIslandById(0).line3() + "   " + getIslandById(1).line3() + "\n");
                board.append("              " + getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "\n\n");

                board.append(getIslandById(9).line1and4() + "                               " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(9).line2() + "                               " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(9).line3() + "                               " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(9).line1and4() + "                               " + getIslandById(2).line1and4() + "\n\n");

                board.append(getIslandById(8).line1and4() + "                               " + getIslandById(3).line1and4() + "\n");
                board.append(getIslandById(8).line2() + "                               " + getIslandById(3).line2() + "\n");
                board.append(getIslandById(8).line3() + "                               " + getIslandById(3).line3() + "\n");
                board.append(getIslandById(8).line1and4() + "                               " + getIslandById(3).line1and4() + "\n\n");

                board.append(getIslandById(7).line1and4() + "                               " + getIslandById(4).line1and4() + "\n");
                board.append(getIslandById(7).line2() + "                               " + getIslandById(4).line2() + "\n");
                board.append(getIslandById(7).line3() + "                               " + getIslandById(4).line3() + "\n");
                board.append(getIslandById(7).line1and4() + "                               " + getIslandById(4).line1and4() + "\n\n");

                board.append("              " + getIslandById(6).line1and4() + "   " + getIslandById(5).line1and4() + "\n");
                board.append("              " + getIslandById(6).line2() + "   " + getIslandById(5).line2() + "\n");
                board.append("              " + getIslandById(6).line3() + "   " + getIslandById(5).line3() + "\n");
                board.append("              " + getIslandById(6).line1and4() + "   " + getIslandById(5).line1and4() + "\n\n");
            }

            case 11 -> {
                board.append("              " + getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "\n");
                board.append("              " + getIslandById(0).line2() + "   " + getIslandById(1).line2() + "\n");
                board.append("              " + getIslandById(0).line3() + "   " + getIslandById(1).line3() + "\n");
                board.append("              " + getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "\n\n");

                board.append(getIslandById(10).line1and4() + "                               " + getIslandById(2).line1and4() + "\n");
                board.append(getIslandById(10).line2() + "                               " + getIslandById(2).line2() + "\n");
                board.append(getIslandById(10).line3() + "                               " + getIslandById(2).line3() + "\n");
                board.append(getIslandById(10).line1and4() + "                               " + getIslandById(2).line1and4() + "\n\n");

                board.append(getIslandById(9).line1and4() + "                               " + getIslandById(3).line1and4() + "\n");
                board.append(getIslandById(9).line2() + "                               " + getIslandById(3).line2() + "\n");
                board.append(getIslandById(9).line3() + "                               " + getIslandById(3).line3() + "\n");
                board.append(getIslandById(9).line1and4() + "                               " + getIslandById(3).line1and4() + "\n\n");

                board.append(getIslandById(8).line1and4() + "                               " + getIslandById(4).line1and4() + "\n");
                board.append(getIslandById(8).line2() + "                               " + getIslandById(4).line2() + "\n");
                board.append(getIslandById(8).line3() + "                               " + getIslandById(4).line3() + "\n");
                board.append(getIslandById(8).line1and4() + "                               " + getIslandById(4).line1and4() + "\n\n");

                board.append(getIslandById(7).line1and4() + "   " + getIslandById(6).line1and4() + "   " + getIslandById(5).line1and4() + "\n");
                board.append(getIslandById(7).line2() + "   " + getIslandById(6).line2() + "   " + getIslandById(5).line2() + "\n");
                board.append(getIslandById(7).line3() + "   " + getIslandById(6).line3() + "   " + getIslandById(5).line3() + "\n");
                board.append(getIslandById(7).line1and4() + "   " + getIslandById(6).line1and4() + "   " + getIslandById(5).line1and4() + "\n\n");
            }

            case 12 -> {
                board.append(getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "   " + getIslandById(2).line1and4() + "   " + getIslandById(3).line1and4() + "\n");
                board.append(getIslandById(0).line2() + "   " + getIslandById(1).line2() + "   " + getIslandById(2).line2() + "   " + getIslandById(3).line2() + "\n");
                board.append(getIslandById(0).line3() + "   " + getIslandById(1).line3() + "   " + getIslandById(2).line3() + "   " + getIslandById(3).line3() + "\n");
                board.append(getIslandById(0).line1and4() + "   " + getIslandById(1).line1and4() + "   " + getIslandById(2).line1and4() + "   " + getIslandById(3).line1and4() + "\n\n");

                board.append(getIslandById(11).line1and4() + "                                                           " + getIslandById(4).line1and4() + "\n");
                board.append(getIslandById(11).line2() + "                                                           " + getIslandById(4).line2() + "\n");
                board.append(getIslandById(11).line3() + "                                                           " + getIslandById(4).line3() + "\n");
                board.append(getIslandById(11).line1and4() + "                                                           " + getIslandById(4).line1and4() + "\n\n");

                board.append(getIslandById(10).line1and4() + "                                                           " + getIslandById(5).line1and4() + "\n");
                board.append(getIslandById(10).line2() + "                                                           " + getIslandById(5).line2() + "\n");
                board.append(getIslandById(10).line3() + "                                                           " + getIslandById(5).line3() + "\n");
                board.append(getIslandById(10).line1and4() + "                                                           " + getIslandById(5).line1and4() + "\n\n");

                board.append(getIslandById(9).line1and4() + "   " + getIslandById(8).line1and4() + "   " + getIslandById(7).line1and4() + "   " + getIslandById(6).line1and4() + "\n");
                board.append(getIslandById(9).line2() + "   " + getIslandById(8).line2() + "   " + getIslandById(7).line2() + "   " + getIslandById(6).line2() + "\n");
                board.append(getIslandById(9).line3() + "   " + getIslandById(8).line3() + "   " + getIslandById(7).line3() + "   " + getIslandById(6).line3() + "\n");
                board.append(getIslandById(9).line1and4() + "   " + getIslandById(8).line1and4() + "   " + getIslandById(7).line1and4() + "   " + getIslandById(6).line1and4() + "\n\n");
            }

            default -> {
                return "ERROR";
            }
        }
        return board.toString();
    }

    public String printCardsPlayed() {
        StringBuilder string = new StringBuilder();
        string.append("Assistant Cards played: \n\n");
        for(AssistantCard a : cardsPlayed)
            string.append(a.header() + "   ");
        string.append("\n");
        for(AssistantCard a : cardsPlayed)
            string.append(a.line1and4() + "   ");
        string.append("\n");
        for(AssistantCard a : cardsPlayed)
            string.append(a.line2() + "   ");
        string.append("\n");
        for(AssistantCard a : cardsPlayed)
            string.append(a.line3() + "   ");
        string.append("\n");
        for(AssistantCard a : cardsPlayed)
            string.append(a.line1and4() + "   ");
        string.append("\n");
        return string.toString();
    }

    public String printDeck(){
        StringBuilder string = new StringBuilder();
        boolean twoLines = deck.size()>5;
        string.append("Your Deck: \n\n");
        if(!twoLines) {
            for (AssistantCard a : deck)
                string.append(a.line1and4() + "   ");
            string.append("\n");
            for (AssistantCard a : deck)
                string.append(a.line2() + "   ");
            string.append("\n");
            for (AssistantCard a : deck)
                string.append(a.line3() + "   ");
            string.append("\n");
            for (AssistantCard a : deck)
                string.append(a.line1and4() + "   ");
            string.append("\n");
            return string.toString();
        }

        for(int i=0; i<5; i++)
            string.append(deck.get(i).line1and4() + "   ");
        string.append("\n");
        for(int i=0; i<5; i++)
            string.append(deck.get(i).line2() + "   ");
        string.append("\n");
        for(int i=0; i<5; i++)
            string.append(deck.get(i).line3() + "   ");
        string.append("\n");
        for(int i=0; i<5; i++)
            string.append(deck.get(i).line1and4() + "   ");
        string.append("\n\n");
        for(int i=5; i<deck.size(); i++)
            string.append(deck.get(i).line1and4() + "   ");
        string.append("\n");
        for(int i=5; i<deck.size(); i++)
            string.append(deck.get(i).line2() + "   ");
        string.append("\n");
        for(int i=5; i<deck.size(); i++)
            string.append(deck.get(i).line3() + "   ");
        string.append("\n");
        for(int i=5; i<deck.size(); i++)
            string.append(deck.get(i).line1and4() + "   ");
        string.append("\n");

        return string.toString();
    }

    public String separator(){
        return "-------------------------------------------------------------------------------------------------------------------\n";
    }

    @Override
    public String toString() {
        String toPrint = "\n\n" + separator() + "\nIslands:\n\n" + printIslands() + separator() +
                "\nClouds:\n\n" + printClouds() + "\n" + separator() +
                "\nSchools:\n\n" + printSchools() + "\n" + separator() +
                "\n" + printCardsPlayed() + "\n" + separator() +
                "\n" + printDeck() + "\n" + separator();
        return toPrint.replaceAll("\n", "\n     ").replaceAll("     -----", "----------");
    }

    private Island getIslandById(int id) {
        for(Island island : islands)
            if(island.id == id)
                return island;
        return null;
    }

    private Cloud getCloudById(int id) {
        for(Cloud cloud : clouds)
            if(cloud.id == id)
                return cloud;
        return null;
    }
}
