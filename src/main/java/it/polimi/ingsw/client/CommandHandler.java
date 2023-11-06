package it.polimi.ingsw.client;

import it.polimi.ingsw.client.views.BoardView;
import it.polimi.ingsw.client.views.View;
import it.polimi.ingsw.resources.ActionPhase1Move;
import it.polimi.ingsw.resources.GameState;
import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.GamePhase;
import it.polimi.ingsw.server.model.expert.CharacterCard;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandHandler implements Runnable{
    public AtomicBoolean mustStop;
    private BoardView view;

    public CommandHandler(BoardView view) {
        mustStop = new AtomicBoolean(false);
        this.view = view;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Scanner input = new Scanner(System.in);
            while (!mustStop.get()) {
                String command = input.nextLine();
                handleCommand(command);
            }
        }
    }

    public void stop() {
        mustStop.set(true);
    }

    private void handleCommand(String c) {
        String[] command = c.split(" ");
        switch (command[0].toLowerCase()) {
            case "refresh" -> view.refresh();

            case "playassistantcard" -> {
                if (command.length == 2) {
                    if (view.getClient().getServerObserver().gamePhase == GamePhase.PLANNING_PHASE) {
                        try {
                            int param = Integer.parseInt(command[1]);
                            if (param > 0 && param < 11)
                                view.getClient().getServerObserver().playAssistantCard(param);
                            else
                                View.writeLine("The selected assistant card doesn't exits");
                        } catch (NumberFormatException e) {
                            commandError(command[0]);
                        }
                    } else
                        View.writeLine("You can't perform this action now!");
                } else
                    commandError(command[0]);
            }

            case "movestudents" -> {
                if (view.getClient().getServerObserver().gamePhase == GamePhase.ACTION_PHASE1) {
                    Scanner input = new Scanner(System.in);
                    int islandNumber = view.getGameState().getBoard().getIslands().size();
                    int moveNumber, i = 0;
                    if (view.getClient().getServerObserver().getInfo().getPlayerNumber() == 2 || view.getClient().getServerObserver().getInfo().getPlayerNumber() == 4)
                        moveNumber = 3;
                    else
                        moveNumber = 4;
                    List<ActionPhase1Move> actions = new ArrayList<>();
                    int currentID = view.getClient().getServerObserver().getInfo().getPlayerId();
                    List<Color> studentsList = new ArrayList<>(view.getGameState().getPlayers().get(view.getGameState().getPlayerById(currentID).getId()).getSchool().getEntrance());
                    while (i < moveNumber) {
                        View.writeLine("Insert the color of the student and the position in which you want to move that student\n" +
                                "(if you want to move a student to the dining room write hall, otherwise the chosen island's ID)");
                        String studentPosition = input.nextLine();
                        String[] moves = studentPosition.split(" ");
                        Color color = null;
                        switch (moves[0].toUpperCase()) {
                            case "RED" -> {
                                if (!studentsList.contains(Color.RED)) {
                                    View.writeLine("You do not have any student of this color!");
                                    continue;
                                } else {
                                    color = Color.RED;
                                }
                            }
                            case "YELLOW" -> {
                                if (!studentsList.contains(Color.YELLOW)) {
                                    View.writeLine("You do not have any student of this color!");
                                    continue;
                                } else {
                                    color = Color.YELLOW;
                                }
                            }
                            case "GREEN" -> {
                                if (!studentsList.contains(Color.GREEN)) {
                                    View.writeLine("You do not have any student of this color!");
                                    continue;
                                } else {
                                    color = Color.GREEN;
                                }
                            }
                            case "BLUE" -> {
                                if (!studentsList.contains(Color.BLUE)) {
                                    View.writeLine("You do not have any student of this color!");
                                    continue;
                                } else {
                                    color = Color.BLUE;
                                }
                            }
                            case "PINK" -> {
                                if (!studentsList.contains(Color.PINK)) {
                                    View.writeLine("You do not have any student of this color!");
                                    continue;
                                } else {
                                    color = Color.PINK;
                                }
                            }
                            default -> {
                                View.writeLine("Insert a valid students' color!");
                                continue;
                            }
                        }

                        if (moves[1].equalsIgnoreCase("hall")) {
                            actions.add(ActionPhase1Move.inDiningRoom(color));
                            studentsList.remove(color);
                            i++;
                        } else
                            try {
                                if (0 <= Integer.parseInt(moves[1]) && Integer.parseInt(moves[1]) < islandNumber) {
                                    actions.add(ActionPhase1Move.onIsland(color, Integer.parseInt(moves[1])));
                                    studentsList.remove(color);
                                    i++;
                                } else
                                    View.writeLine("Insert a valid island's number!");
                            } catch (NumberFormatException e) {
                                View.writeLine("Insert a valid island's number!");
                            }
                    }
                    view.getClient().getServerObserver().moveStudents(actions);
                } else
                    View.writeLine("You can't perform this action now!");
            }

            case "movemothernature" -> {
                if (view.getClient().getServerObserver().gamePhase == GamePhase.ACTION_PHASE2) {
                    if (command.length == 2) {
                        try {
                            int motherNatureMovesTo = Integer.parseInt(command[1]);
                            view.getClient().getServerObserver().moveMotherNature(motherNatureMovesTo);
                        } catch (NumberFormatException e) {
                            View.writeLine("Insert a number!");
                        }
                    } else
                        commandError("move mother nature");
                } else
                    View.writeLine("You cannot perform this action now!");
            }

            case "choosecloud" -> {
                if (view.getClient().getServerObserver().gamePhase == GamePhase.ACTION_PHASE3) {
                    if (command.length == 2) {
                        try {
                            int param = Integer.parseInt(command[1]);
                            if (param >= 0 && param < view.getGameState().getPlayers().size()) {
                                for (GameState.Board.Cloud cloud : view.getGameState().getBoard().getClouds())
                                    if (cloud.getId() == param) {
                                        if (cloud.getStudents().isEmpty())
                                            View.writeLine("The selected cloud is empty, try again!");
                                        else
                                            view.getClient().getServerObserver().chooseCloud(param);
                                    } else
                                        View.writeLine("The selected cloud does not exists");
                            }
                        } catch (NumberFormatException e) {
                            commandError(command[0]);
                        }
                    } else
                        commandError(command[0]);

                } else
                    View.writeLine("You cannot perform this action now!");
            }

            case "playcharactercard" -> {
                if(view.getClient().getServerObserver().getInfo().isExpertMode()) {
                    if (view.getClient().getServerObserver().gamePhase == GamePhase.ACTION_PHASE1 || view.getClient().getServerObserver().gamePhase == GamePhase.ACTION_PHASE2 || view.getClient().getServerObserver().gamePhase == GamePhase.ACTION_PHASE3) {
                        if (command.length == 2) {
                            try {
                                int characterCardNumber = Integer.parseInt(command[1]);
                                characterCardNumber--;
                                if (characterCardNumber >= 0 && characterCardNumber < 3) {
                                    int charCardId = view.getGameState().getCharacterCards()[characterCardNumber].getId();
                                    if (charCardId == 9) {
                                        boolean emptyHall = true;
                                        for (Color color : view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getDiningRoom().keySet())
                                            if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getDiningRoom().get(color) > 0)
                                                emptyHall = false;
                                        if (emptyHall) {
                                            View.writeLine("You can't use this card with an empty hall!");
                                            return;
                                        }
                                    }
                                    view.getClient().getServerObserver().playCharacterCard(characterCardNumber);
                                    while (view.getClient().getServerObserver().characterCardResponse == 0)
                                        View.write("");
                                    if (view.getClient().getServerObserver().characterCardResponse == -1)
                                        return;
                                    Scanner input = new Scanner(System.in);
                                    switch (charCardId) {
                                        case 0: {
                                            Color student = null;
                                            int islandId = -1;
                                            while (student == null || islandId == -1) {
                                                View.writeLine("Choose the student to move and the destination island");
                                                String cmd = input.nextLine();
                                                String[] cmdArray = cmd.split(" ");
                                                switch (cmdArray[0].toLowerCase()) {
                                                    case "red" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.RED))
                                                            student = Color.RED;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    case "blue" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.BLUE))
                                                            student = Color.BLUE;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    case "pink" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.PINK))
                                                            student = Color.PINK;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    case "yellow" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.YELLOW))
                                                            student = Color.YELLOW;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    case "green" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.GREEN))
                                                            student = Color.GREEN;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    default -> View.writeLine("Invalid color. Try again!");
                                                }
                                                try {
                                                    int id = Integer.parseInt(cmdArray[1]);
                                                    if (id >= 0 && id < view.getGameState().getBoard().getIslands().size())
                                                        islandId = id;
                                                    else
                                                        View.writeLine("Island id not exists");
                                                } catch (NumberFormatException e) {
                                                    commandError(command[0]);
                                                }

                                            }
                                            view.getClient().getServerObserver().activateCharacterCardEffect(student, islandId);
                                            break;
                                        }
                                        case 10: {
                                            Color student = null;
                                            while (student == null) {
                                                View.writeLine("Choose the student");
                                                String color = input.nextLine();
                                                switch (color.toLowerCase()) {
                                                    case "red" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.RED))
                                                            student = Color.RED;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    case "blue" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.BLUE))
                                                            student = Color.BLUE;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    case "pink" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.PINK))
                                                            student = Color.PINK;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    case "yellow" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.YELLOW))
                                                            student = Color.YELLOW;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    case "green" -> {
                                                        if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.GREEN))
                                                            student = Color.GREEN;
                                                        else
                                                            View.writeLine("Color not available");
                                                    }
                                                    default -> View.writeLine("Invalid color. Try again!");
                                                }
                                            }
                                            view.getClient().getServerObserver().activateCharacterCardEffect(student, null);
                                            break;
                                        }
                                        case 2:
                                        case 4: {
                                            int islandId = -1;
                                            while (islandId == -1) {
                                                View.writeLine("Choose an island");
                                                try {
                                                    int id = Integer.parseInt(input.nextLine());
                                                    if (id >= 0 && id < view.getGameState().getBoard().getIslands().size())
                                                        islandId = id;
                                                    else
                                                        View.writeLine("Island id not exists");
                                                } catch (NumberFormatException e) {
                                                    commandError(command[0]);
                                                }
                                            }
                                            view.getClient().getServerObserver().activateCharacterCardEffect(islandId, null);
                                            break;
                                        }
                                        case 6: {
                                            List<Color> studentsOnCard = new ArrayList<>();
                                            List<Color> studentsInEntrance = new ArrayList<>();
                                            while (studentsOnCard.isEmpty()) {
                                                View.writeLine("Choose up to 3 students from the character card");
                                                String cmd = input.nextLine();
                                                String[] students = cmd.split(" ");
                                                if (students.length <= 3 && students.length > 0) {
                                                    boolean ok = true;
                                                    for (String color : students) {
                                                        switch (color.toLowerCase()) {
                                                            case "red" -> {
                                                                if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.RED))
                                                                    studentsOnCard.add(Color.RED);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "blue" -> {
                                                                if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.BLUE))
                                                                    studentsOnCard.add(Color.BLUE);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "pink" -> {
                                                                if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.PINK))
                                                                    studentsOnCard.add(Color.PINK);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "yellow" -> {
                                                                if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.YELLOW))
                                                                    studentsOnCard.add(Color.YELLOW);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "green" -> {
                                                                if (view.getGameState().getCharacterCards()[characterCardNumber].getStudents().contains(Color.GREEN))
                                                                    studentsOnCard.add(Color.GREEN);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            default -> {
                                                                View.writeLine("Invalid color. Try again!");
                                                                ok = false;
                                                            }
                                                        }
                                                    }
                                                    if (!ok)
                                                        studentsOnCard.clear();
                                                } else
                                                    commandError(command[0]);
                                            }

                                            while (studentsInEntrance.isEmpty()) {
                                                View.writeLine("Choose " + studentsOnCard.size() + " students from your entrance");
                                                String cmd = input.nextLine();
                                                String[] students = cmd.split(" ");
                                                if (students.length == studentsOnCard.size()) {
                                                    boolean ok = true;
                                                    for (String color : students) {
                                                        switch (color.toLowerCase()) {
                                                            case "red" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.RED))
                                                                    studentsInEntrance.add(Color.RED);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "blue" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.BLUE))
                                                                    studentsInEntrance.add(Color.BLUE);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "pink" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.PINK))
                                                                    studentsInEntrance.add(Color.PINK);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "yellow" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.YELLOW))
                                                                    studentsInEntrance.add(Color.YELLOW);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "green" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.GREEN))
                                                                    studentsInEntrance.add(Color.GREEN);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            default -> {
                                                                View.writeLine("Invalid color. Try again!");
                                                                ok = false;
                                                            }
                                                        }
                                                    }
                                                    if (!ok)
                                                        studentsInEntrance.clear();
                                                } else
                                                    commandError(command[0]);
                                            }
                                            view.getClient().getServerObserver().activateCharacterCardEffect(studentsOnCard, studentsInEntrance);
                                            break;
                                        }
                                        case 8:
                                        case 11: {
                                            Color color = null;
                                            while (color == null) {
                                                View.writeLine("Choose a color");
                                                String colorChoosen = input.nextLine();
                                                switch (colorChoosen.toLowerCase()) {
                                                    case "red" -> {
                                                        color = Color.RED;
                                                    }
                                                    case "blue" -> {
                                                        color = Color.BLUE;
                                                    }
                                                    case "pink" -> {
                                                        color = Color.PINK;
                                                    }
                                                    case "yellow" -> {
                                                        color = Color.YELLOW;
                                                    }
                                                    case "green" -> {
                                                        color = Color.GREEN;
                                                    }
                                                    default -> View.writeLine("Invalid color. Try again!");
                                                }

                                            }
                                            view.getClient().getServerObserver().activateCharacterCardEffect(color, null);
                                            break;
                                        }
                                        case 9: {
                                            List<Color> studentsInHall = new ArrayList<>();
                                            List<Color> studentsInEntrance = new ArrayList<>();
                                            while (studentsInHall.isEmpty()) {
                                                View.writeLine("Choose up to 2 students from your hall");
                                                String cmd = input.nextLine();
                                                String[] students = cmd.split(" ");
                                                if (students.length <= 2 && students.length > 0) {
                                                    boolean ok = true;
                                                    Map<Color, Integer> hall = new HashMap<>();
                                                    hall.putAll(view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getDiningRoom());
                                                    for (String color : students) {
                                                        switch (color.toLowerCase()) {
                                                            case "red" -> {
                                                                if (hall.get(Color.RED) > 0) {
                                                                    studentsInHall.add(Color.RED);
                                                                    hall.put(Color.RED, hall.get(Color.RED) - 1);
                                                                } else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "blue" -> {
                                                                if (hall.get(Color.BLUE) > 0) {
                                                                    studentsInHall.add(Color.BLUE);
                                                                    hall.put(Color.BLUE, hall.get(Color.BLUE) - 1);
                                                                } else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "pink" -> {
                                                                if (hall.get(Color.PINK) > 0) {
                                                                    studentsInHall.add(Color.PINK);
                                                                    hall.put(Color.PINK, hall.get(Color.PINK) - 1);
                                                                } else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "yellow" -> {
                                                                if (hall.get(Color.YELLOW) > 0) {
                                                                    studentsInHall.add(Color.YELLOW);
                                                                    hall.put(Color.YELLOW, hall.get(Color.YELLOW) - 1);
                                                                } else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "green" -> {
                                                                if (hall.get(Color.GREEN) > 0) {
                                                                    studentsInHall.add(Color.GREEN);
                                                                    hall.put(Color.GREEN, hall.get(Color.GREEN) - 1);
                                                                } else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            default -> {
                                                                View.writeLine("Invalid color. Try again!");
                                                                ok = false;
                                                            }
                                                        }
                                                    }
                                                    if (!ok)
                                                        studentsInHall.clear();
                                                } else
                                                    commandError(command[0]);
                                            }

                                            while (studentsInEntrance.isEmpty()) {
                                                View.writeLine("Choose " + studentsInHall.size() + " students from your entrance");
                                                String cmd = input.nextLine();
                                                String[] students = cmd.split(" ");
                                                if (students.length == studentsInHall.size()) {
                                                    boolean ok = true;
                                                    for (String color : students) {
                                                        switch (color.toLowerCase()) {
                                                            case "red" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.RED))
                                                                    studentsInEntrance.add(Color.RED);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "blue" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.BLUE))
                                                                    studentsInEntrance.add(Color.BLUE);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "pink" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.PINK))
                                                                    studentsInEntrance.add(Color.PINK);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "yellow" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.YELLOW))
                                                                    studentsInEntrance.add(Color.YELLOW);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            case "green" -> {
                                                                if (view.getGameState().getPlayerById(view.getClient().getServerObserver().getInfo().getPlayerId()).getSchool().getEntrance().contains(Color.GREEN))
                                                                    studentsInEntrance.add(Color.GREEN);
                                                                else {
                                                                    View.writeLine("Color not available");
                                                                    ok = false;
                                                                }
                                                            }
                                                            default -> {
                                                                View.writeLine("Invalid color. Try again!");
                                                                ok = false;
                                                            }
                                                        }
                                                    }
                                                    if (!ok)
                                                        studentsInEntrance.clear();
                                                } else
                                                    commandError(command[0]);
                                            }
                                            view.getClient().getServerObserver().activateCharacterCardEffect(studentsInEntrance, studentsInHall);
                                            break;
                                        }
                                    }
                                } else
                                    View.writeLine("The selected character card does not exists");
                            } catch (NumberFormatException e) {
                                commandError(command[0]);
                            }
                        } else
                            commandError(command[0]);
                    } else
                        View.writeLine("You cannot perform this action now!");
                } else
                    View.writeLine("This command is only available in expert mode");
                }

            case "info" -> {
                if (view.getClient().getServerObserver().getInfo().isExpertMode())
                    View.write("These are the commands you can use:\n" +
                            "   - refresh: use this to refresh the view\n" +
                            "   - playassistantcard n: use this to play the assistant card you want, where n represent the speedvalue of the chosen card\n" +
                            "   - movestudents: use this to enter in the phase where you can move your students, then follow the given instruction\n" +
                            "   - movemothernature n: use this to move mother nature to another island, n represents the chosen island's ID\n" +
                            "   - choosecloud n: use this to choose the cloud you want, n represents the chosen cloud's ID\n" +
                            "   - playcharactercard n: use this if in expert mode, this command lets you use a card, n is the card's ID\n" +
                            "   - infocharactercard: use this to read the cards' effects in the game\n\n");
                else
                    View.write("These are the commands you can use:\n" +
                            "   - refresh: use this to refresh the view\n" +
                            "   - playassistantcard n: use this to play the assistant card you want, where n represent the speedvalue of the chosen card\n" +
                            "   - movestudents: use this to enter in the phase where you can move your students, then follow the given instruction\n" +
                            "   - movemothernature n: use this to move mother nature to another island, n represents the chosen island's ID\n" +
                            "   - choosecloud n: use this to choose the cloud you want, n represents the chosen cloud's ID\n\n");
            }

            case "infocharactercard" -> {
                for (int i = 0; i < 3; i++) {
                    switch (view.getGameState().getCharacterCards()[i].getId()){
                        case 0 -> View.writeLine(" MONK: Take 1 Student from this card and place it on an Island of your choice.\n" +
                                                        "       Then, draw a new Student from the Bag and place it on this card.\n");
                        case 1 -> View.writeLine(" FARMER: During this turn, you take control of any number of Professors, even if you have the same number of Students as the player who currently controls them.\n");
                        case 2 -> View.writeLine(" HERALD: Choose an Island and resolve the Island as if Mother Nature had ended her movement there.\n" +
                                                        "         Mother Nature will still move and the Island where she ends her movement will also be resolved.\n");
                        case 3 -> View.writeLine(" POSTMAN: You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played.\n");
                        case 4 -> View.writeLine(" GRANDMOTHER: Place a No Entry tile on an Island of your choice.\n" +
                                                        "              The first time Mother Nature ends her movement there, put the No Entry tile back onto this card.\n" +
                                                        "              DO NOT calculate influence on that Island, or place any Towers.\n");
                        case 5 -> View.writeLine(" CENTAUR: When resolving a Conquering on an Island, Towers do not count towards influence.\n");
                        case 6 -> View.writeLine(" JESTER: You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance.\n");
                        case 7 -> View.writeLine(" KNIGHT: During the influence calculation this turn, you count as having 2 more influence.\n");
                        case 8 -> View.writeLine(" MUSHROOM-MAN: Choose a color of Student; during the influence calculation this turn, that color adds no influence.\n");
                        case 9 -> View.writeLine(" MINSTREL: You may exchange up to 2 Students between your Entrance and your Dining Room.\n");
                        case 10 -> View.writeLine(" PRINCESS: Take 1 Student from this card and place it in your Dining Room.\n" +
                                                         "           Then, draw a new Student from the Bag and place it on this card.\n");
                        case 11 -> View.writeLine(" THIEF: Choose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the bag.\n" +
                                                        "        If any player has fewer than 3 Students of that type, return as many Students as they have.\n");
                    }
                }
            }

            default -> View.writeLine("Invalid command");
            }
    }

    private void commandError(String command) {
        View.writeLine("Wrong usage of command " + command);
    }
}
