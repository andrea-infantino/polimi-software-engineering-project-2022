package it.polimi.ingsw.resources;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.TowerColor;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.expert.CharacterCard;
import it.polimi.ingsw.server.model.expert.ExpertGame;
import it.polimi.ingsw.server.model.expert.ExpertPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState implements Serializable {
    private List<Player> players;
    private Board board;
    private Map<Integer, AssistantCard> cardsPlayed;

    private CharacterCard[] characterCards;
    private int remainingCoins;

    public GameState(Game game) {
        players = new ArrayList<>();
        board = new Board(game.getBoard());
        for(it.polimi.ingsw.server.model.Player p : game.getPlayers())
            players.add(new Player(p));

        for(Team t : game.getTeams()) {
            for(Player p : players)
                if(t.getPlayers().get(0) == p.id)
                    p.school.towers = t.towers;
        }

        cardsPlayed = new HashMap<>();
        cardsPlayed.putAll(game.currentRound.getAssistantCardsPlayed());

        if(game.isExpertMode()) {
            characterCards = new CharacterCard[3];
            for(int i=0; i<3; i++)
                characterCards[i] = new CharacterCard(((ExpertGame) game).getCharacterCards()[i]);
            remainingCoins = ((ExpertGame) game).leftCoins;
            for(Player p : players)
                p.coins = ((ExpertPlayer) game.getPlayerById(p.id)).getCoins();
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerById(int id) {
        for(Player p : players)
            if (id == p.getId())
                return p;
        return null;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Integer, AssistantCard> getCardsPlayed() {
        return cardsPlayed;
    }

    public CharacterCard[] getCharacterCards() {
        return characterCards;
    }

    public int getRemainingCoins() {
        return remainingCoins;
    }

    public class CharacterCard implements Serializable {
        int id;
        int cost;
        int remainingNoEntryTiles;
        List<Color> students;

        CharacterCard(it.polimi.ingsw.server.model.expert.CharacterCard c) {
            this.id = c.getId();
            this.cost = c.getCost();
            this.remainingNoEntryTiles = c.remainingNoEntryTiles;
            this.students = new ArrayList<>();
            this.students.addAll(c.getStudents());
        }

        public int getId() {
            return id;
        }

        public int getCost() {
            return cost;
        }

        public int getRemainingNoEntryTiles() {
            return remainingNoEntryTiles;
        }

        public List<Color> getStudents() {
            return students;
        }
    }
    public class Board implements Serializable {
        List<Island> islands;
        List<Cloud> clouds;
        Map<Color, Boolean> professors;

        Board (it.polimi.ingsw.server.model.Board board) {
            islands = new ArrayList<>();
            for(it.polimi.ingsw.server.model.IslandGroup i : board.getIslandGroups())
                islands.add(new Island(i));
            clouds = new ArrayList<>();
            for(it.polimi.ingsw.server.model.Cloud c : board.getClouds())
                clouds.add(new Cloud(c));
            professors = new HashMap<>();
            professors.putAll(board.professors);
        }

        public List<Island> getIslands() {
            return islands;
        }

        public List<Cloud> getClouds() {
            return clouds;
        }

        public Map<Color, Boolean> getProfessors() {
            return professors;
        }

        public class Cloud implements Serializable {
            int id;
            List<Color> students;

            Cloud(it.polimi.ingsw.server.model.Cloud c) {
                id = c.getId();
                students = new ArrayList<>(c.getStudents());
            }

            public int getId(){
                return id;
            }

            public List<Color> getStudents() {
                return students;
            }
        }
        public class Island implements Serializable {
            int id;
            int islandNumber;
            List<Color> students;
            boolean hasMotherNature;
            boolean hasNoEntryTile;
            TowerColor controlledBy;

            Island (it.polimi.ingsw.server.model.IslandGroup island) {
                id = island.getId();
                islandNumber = island.islandsNumber;
                students = new ArrayList<>(island.getStudents());
                hasMotherNature = island.hasMotherNature;
                hasNoEntryTile = island.hasNoEntryTile;
                controlledBy = island.controlledBy;
            }

            public int getId() {
                return id;
            }

            public int getIslandNumber() {
                return islandNumber;
            }

            public List<Color> getStudents() {
                return students;
            }

            public boolean isHasMotherNature() {
                return hasMotherNature;
            }

            public boolean isHasNoEntryTile() {
                return hasNoEntryTile;
            }

            public TowerColor getControlledBy() {
                return controlledBy;
            }
        }

    }
    public class Player implements Serializable {
        int id;
        School school;
        List<AssistantCard> deck;

        int coins;

        Player(it.polimi.ingsw.server.model.Player player) {
            id = player.getId();
            deck = new ArrayList<>(player.getDeck());
            school = new School(player.getSchool());
        }

        public int getId() {
            return id;
        }

        public School getSchool() {
            return school;
        }

        public List<AssistantCard> getDeck() {
            return deck;
        }

        public int getCoins() {
            return coins;
        }

        public class School implements Serializable {
            Map<Color, Integer> diningRoom;
            Map<Color, Boolean> professors;
            List<Color> entrance;
            int towers;

            School(it.polimi.ingsw.server.model.School school) {
                diningRoom = new HashMap<>(school.getDiningRoom());
                professors = new HashMap<>(school.professor);
                entrance = new ArrayList<>(school.getStudents());
            }

            public Map<Color, Boolean> getProfessors() {
                return professors;
            }

            public Map<Color, Integer> getDiningRoom() {
                return diningRoom;
            }

            public List<Color> getEntrance() {
                return entrance;
            }

            public int getTowers() {
                return towers;
            }
        }
    }
}
