package it.polimi.ingsw.server.model.expert;

import it.polimi.ingsw.server.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represent a single game in expert mode
 */
public class ExpertGame extends Game {
    public int leftCoins;
    private CharacterCard[] characterCards;

    /**
     * constructor
     * @param expertMode a boolean representing whether the game is in expert mode or not
     * @param playerNumber the number of players playing in the game
     */
    public ExpertGame (boolean expertMode, int playerNumber){
        super(expertMode, playerNumber);
        this.leftCoins = 20 - playerNumber;
        this.characterCards = new CharacterCard[3];
        List<Integer> cardPick = new ArrayList<>();
        for (int i = 0; i < 12 ; i++){
            cardPick.add(i);
        }
        for (int i = 0; i < 3 ; i++){
            int randPick = (int) (Math.random() * cardPick.size());
            int randId = cardPick.get(randPick);
            cardPick.remove(randPick);
            characterCards[i] = new CharacterCard(randId);
        }

    }

    /**
     * method that represents the fact that a player plays a character card
     * @param expertPlayer the player that wants to play a character card
     * @param characterCardNumber the id of the character card that the player wants to play
     */
    public void playCharacterCard(ExpertPlayer expertPlayer, int characterCardNumber){
        if (expertPlayer.getCoins() >= characterCards[characterCardNumber].getCost()) {
            expertPlayer.removeCoins(characterCards[characterCardNumber].getCost());
            leftCoins += characterCards[characterCardNumber].getCost();
            characterCards[characterCardNumber].increaseCost();
        }
    }

    /**
     * request for the 3 character cards in the game
     * @return the 3 character cards in the game
     */
    public CharacterCard[] getCharacterCards() {
        return characterCards;
    }
}
