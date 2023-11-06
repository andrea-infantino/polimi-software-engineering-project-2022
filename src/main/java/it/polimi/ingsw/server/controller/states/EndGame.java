package it.polimi.ingsw.server.controller.states;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.Team;

/**
 * Class that represent End Game state
 */
public class EndGame implements GameControllerState {

    /**
     * Request to calculate the team winner (who has more towers?)
     * @return the winner team id
     */
    private int getWinner(){
        int minTowers = 9;
        for(Team t : Server.game.getTeams())
            if(t.towers < minTowers)
                minTowers = t.towers;
        List<Integer> possibleWinners = new ArrayList<>();
        for(Team t : Server.game.getTeams())
            if(t.towers == minTowers)
                possibleWinners.add(t.getId());
        if(possibleWinners.size() == 1)
            return possibleWinners.get(0);
        int winnerTeamId = -1;
        int maxProfessors = -1;
        for(Integer i : possibleWinners)
            if(getTeamProfessors(i) > maxProfessors) {
                winnerTeamId = i;
                maxProfessors = getTeamProfessors(i);
            }
        return winnerTeamId;

    }

    /**
     * Calculate how many professors that team has
     * @param teamId
     * @return an integer that represent how many professors that team has
     */
    private int getTeamProfessors (int teamId) {
        Team team = Server.game.getTeamById(teamId);
        int n = 0;
        for(int playerId : team.getPlayers())
            for(Color c : Server.game.getPlayerById(playerId).getSchool().professor.keySet())
                if(Server.game.getPlayerById(playerId).getSchool().professor.get(c))
                    n++;
        return n;
    }


    @Override
    public void nextState(GameController g) {

    }

    @Override
    public void Action(Object o_null1, Object o_null2) {
        Server.game.winnerTeamId = getWinner();
    }
}
