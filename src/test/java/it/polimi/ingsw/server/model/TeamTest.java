package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    Team team2, team3, team4;
    List<Integer> playersList;

    @BeforeEach
    void setUp(){
        team2 = new Team(0, 0, 2);
        team3 = new Team(1,1,3);
        team4 = new Team(2);
        playersList = new ArrayList<>();


    }

    @Test
    public void TestConstructorsTeam(){
        assertEquals(1, team2.getPlayers().size() );
        assertEquals(1, team3.getPlayers().size());
        assertEquals(8, team2.towers);
        assertEquals(8, team4.towers);
        assertEquals(6, team3.towers);
    }

    @Test
    public void TestAddPlayer(){
        team2.addPlayer(3);
        assertEquals(2, team2.getPlayers().size());
    }

    @Test
    public void TestGetPlayers(){
        team2.addPlayer(3);
        playersList.add(0);
        playersList.add(3);
        assertEquals( playersList, team2.getPlayers());
    }

    @Test
    public void TestGetId(){
        assertEquals(1, team3.getId());
    }

    @Test
    public void TestAllTowersPlaced(){
        team2.towers = 0;
        assertTrue(team2.allTowersPlaced());
    }
}

