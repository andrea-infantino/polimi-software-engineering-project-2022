package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game2;
    Player player0, player1;
    Team team21, team22;
    List<Player> players;
    List<Team> teams;
    Board board;

    @BeforeEach
    void SetUp(){
        game2 = new Game(false, 2);
        player0 = new Player(0, "Ambrogio");
        player1 = new Player(1, "Giulio");
        team21 = new Team(0, 0, 2);
        team22 = new Team(1, 1, 2);
        players = new ArrayList<>();
        teams = new ArrayList<>();
    }

    @Test
    public void TestGame() {
        //Test isExpertMode
        assertFalse(game2.isExpertMode());
        //Test addPlayer
        game2.addPlayer(player0);
        game2.addPlayer(player1);
        players.add(player0);
        players.add(player1);
        //Test addTeam
        game2.addTeam(team21);
        game2.addTeam(team22);
        teams.add(team21);
        teams.add(team22);
        //Test getPlayerNumber
        assertEquals(2, game2.getPlayerNumber());
        //Test getPlayerById
        assertEquals(player0, game2.getPlayerById(0));
        assertNull(game2.getPlayerById(12));
        //Test getTeamById
        assertEquals(team21, game2.getTeamById(0));
        assertNull(game2.getTeamById(23));
        //Test getPlayers
        assertEquals(players, game2.getPlayers());
        //Test getTeams
        assertEquals(teams, game2.getTeams());
        //Test getTeamByTowerColor
        assertEquals(team21, game2.getTeamByTowerColor(TowerColor.WHITE));
        assertNull(game2.getTeamByTowerColor(TowerColor.GREY));
        //Test getBoard
        board = game2.getBoard();
        assertEquals(board, game2.getBoard());
    }
}