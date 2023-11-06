package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.TowerColor;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.Team;
import it.polimi.ingsw.server.model.expert.CharacterCard;
import it.polimi.ingsw.server.model.expert.ExpertGame;

/**
 * class that changes Mother Nature position and calculate influence over the island
 */
public class MoveMotherNature implements GameControllerState {

    @Override
    public void nextState(GameController g) {
        if (checkEndConditions())
            g.setState(new EndGame());
        else
            g.setState(new ChooseCloudTile());
    }

    @Override
    public void Action(Object o_choice, Object null_o) {
        int islandWithMN = (Integer) o_choice;
        Server.game.getBoard().setMotherNatureIsland(islandWithMN);
        islandConquer(islandWithMN);
    }

    /**
     * method that change the owner of the island if possible
     * @param islandId island's identifier of the one in which mother nature has been moved to
     */
    public static void islandConquer(int islandId) {
        if(Server.game.getBoard().getIslandGroupById(islandId).hasNoEntryTile) {
            Server.game.getBoard().getIslandGroupById(islandId).hasNoEntryTile = false;
            for(CharacterCard c : ((ExpertGame) Server.game).getCharacterCards())
                if(c.getId() == 4)
                    c.remainingNoEntryTiles++;
        }
        else {
            for (Team t : Server.game.getTeams()) {
                Server.game.getBoard().getIslandGroupById(islandId).setInfluence(t.towerColor, calculateInfluence(islandId, t));
            }
            int maxInfluence = 0;
            TowerColor strongestTeam = null;
            for (Team t : Server.game.getTeams()) {
                if (Server.game.getBoard().getIslandGroupById(islandId).getInfluence().get(t.towerColor) > maxInfluence) {
                    maxInfluence = Server.game.getBoard().getIslandGroupById(islandId).getInfluence().get(t.towerColor);
                    strongestTeam = t.towerColor;
                }
            }
            if(strongestTeam != null) {
                changeTowers(islandId, strongestTeam);
                mergeIsland(islandId);
            }
        }
    }

    /**
     * method that calculate influence on the island in which mother nature has been moved to
     * @param islandId identifier of the island(s) where mother nature is
     * @param team team that is calculating influence
     * @return the value of influence for the team
     */
    private static int calculateInfluence(int islandId, Team team){
        int influence = 0;
        if (team.towerColor == Server.game.getBoard().getIslandGroupById(islandId).controlledBy && !Server.game.currentRound.currentTurn.card6Played)
            influence += Server.game.getBoard().getIslandGroupById(islandId).islandsNumber;
        for (Color c : Color.values())
            for (Integer p: team.getPlayers())
                if (Server.game.getPlayerById(p).getSchool().professor.get(c) && Server.game.currentRound.currentTurn.card9Played != c)
                    influence += Server.game.getBoard().getIslandGroupById(islandId).getStudentsByColor(c);
        if (team.getPlayers().contains(Server.game.currentRound.currentTurn.getPlayerId()) && Server.game.currentRound.currentTurn.card8Played)
            influence += 2;
        return influence;
    }

    /**
     * method that changes the towers after a conquest of the group of islands
     * @param islandId identifier of the conquered island
     * @param towerColor color of the player's tower that conquered the island(s)
     */
    private static void changeTowers(int islandId, TowerColor towerColor){
        if (Server.game.getBoard().getIslandGroupById(islandId).controlledBy != null) {
            Server.game.getTeamByTowerColor(Server.game.getBoard().getIslandGroupById(islandId).controlledBy).towers += Server.game.getBoard().getMotherNatureIsland().islandsNumber;
        }
        Server.game.getTeamByTowerColor(towerColor).towers -= Server.game.getBoard().getIslandGroupById(islandId).islandsNumber;
        Server.game.getBoard().getIslandGroupById(islandId).controlledBy = towerColor;
    }

    /**
     * method that checks if is the case and then joins two groups of island
     * @param islandId identifier of the conquered island(s)
     */
    private static void mergeIsland(int islandId){
        int previous, next;
        if (islandId == 0)
            previous = Server.game.getBoard().getIslandGroups().size() - 1;
        else
            previous = islandId - 1;
        if (islandId == Server.game.getBoard().getIslandGroups().size() - 1)
            next = 0;
        else
            next = islandId + 1;

        if (Server.game.getBoard().getIslandGroupById(islandId).controlledBy == Server.game.getBoard().getIslandGroupById(next).controlledBy)
            Server.game.getBoard().Join(islandId, next);

        if (islandId == 0)
            previous = Server.game.getBoard().getIslandGroups().size() - 1;
        if (Server.game.getBoard().getIslandGroupById(islandId).controlledBy == Server.game.getBoard().getIslandGroupById(previous).controlledBy)
            Server.game.getBoard().Join(previous, islandId);
    }

    /**
     * check if the end conditions are reached
     * @return true if the game is in end conditions, false otherwise
     */
    public boolean checkEndConditions(){
        for (Team p : Server.game.getTeams()){
            if (p.towers == 0)
                return true;
        }
        return Server.game.getBoard().threeIslandGroups();
    }

}
