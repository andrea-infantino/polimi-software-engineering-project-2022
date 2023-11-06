package it.polimi.ingsw.server.model.expert;

import it.polimi.ingsw.server.model.Player;

/**
 * Class that represent an expert player
 */
public class ExpertPlayer extends Player {
    private int coins;

    /**
     * Request for the coins held by that expert player
     * @return coins held by that player
     */
    public int getCoins(){
        return coins;
    }

    /**
     * Request to add a coin to that expert player
     */
    public void addCoin(){
        this.coins++;
    }

    /**
     * Request to remove coins from that expert player
     * @param numberOfCoins that represent coins held by that player
     */
    public void removeCoins(int numberOfCoins){
        this.coins -= numberOfCoins;
    }

    /**
     * Class ExpertPlayer constructor
     * @param id that is the expert player Id
     * @param name that is the expert player nickname
     */
    public ExpertPlayer(int id, String name){
        super(id, name);
        coins = 1;
    }
}
