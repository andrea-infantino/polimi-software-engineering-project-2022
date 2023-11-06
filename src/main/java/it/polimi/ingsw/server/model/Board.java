package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.TowerColor;

import java.io.Serializable;
import java.util.*;

/**
 * class that represent a single game board
 */
public class Board {
    private Bag bag;
    private List<IslandGroup> islandGroups;
    private List<Cloud> clouds;
    public Map<Color, Boolean> professors;

    /**
     * constructor
     * @param playerNumber number of players playing the game
     */
    public Board(int playerNumber){
        bag = new Bag();
        int i;

        clouds = new ArrayList<>();
        for(i=0; i<playerNumber; i++){
            clouds.add(new Cloud(playerNumber, i));
        }

        islandGroups = new ArrayList<>();
        int motherNatureIslandId = (int) (Math.random() * 12);
        List<Color> initialStudentsColorOnIslands = new ArrayList<>(Arrays.asList(Color.GREEN, Color.GREEN, Color.RED, Color.RED, Color.YELLOW, Color.YELLOW, Color.PINK, Color.PINK, Color.BLUE, Color.BLUE));
        IslandGroup createdIsland;
        for(i=0; i<12; i++){
            createdIsland = new IslandGroup(i, playerNumber);
            if(i == motherNatureIslandId)
                createdIsland.hasMotherNature = true;
            if(i!= motherNatureIslandId && i!=(motherNatureIslandId-6) && i!=(motherNatureIslandId + 6)){
                int random = (int) (Math.random() * initialStudentsColorOnIslands.size());
                Color randomColor = initialStudentsColorOnIslands.get(random);
                createdIsland.addStudent(randomColor);
                initialStudentsColorOnIslands.remove(random);
            }
            islandGroups.add(createdIsland);
        }

        professors = new HashMap<>();
        for(Color c : Color.values())
            professors.put(c, true);
    }

    /**
     * method that puts the students on an empty cloud
     * @param cloud the cloud to refill
     */
    public void refillCloud(Cloud cloud) {
        cloud.addStudents(bag.extract(cloud.getMaxNumber()));
    }

    /**
     * request for the island's id on which mother nature is
     * @return the island's id on which mother nature is
     */
    public IslandGroup getMotherNatureIsland() {
        for(IslandGroup island : islandGroups)
            if(island.hasMotherNature)
                return island;
        return null;
    }

    /**
     * method that checks if there are 3 groups of islands
     * @return true if there are 3 groups of islands, false otherwise
     */
    public boolean threeIslandGroups() {
        return islandGroups.size() <= 3;
    }

    /**
     * request for the clouds ordered in a list
     * @return the list of clouds in game
     */
    public List<Cloud> getClouds() { return clouds; }

    /**
     * method that returns the cloud, given the cloud's id
     * @param id identifier of the cloud
     * @return the cloud that corresponds to the id
     */
    public Cloud getCloudById(int id) {
        for(Cloud cloud : clouds)
            if(cloud.getId() == id)
                return cloud;
        return null;
    }

    /**
     * request for the game's bag
     * @return the bag
     */
    public Bag getBag() { return bag; }

    /**
     * request for the groups of islands in the game
     * @return a list of groups
     */
    public List<IslandGroup> getIslandGroups() { return islandGroups; }

    /**
     * method that returns the group of islands, given the id
     * @param id identifier of the group of islands
     * @return the group that corresponds to the id
     */
    public IslandGroup getIslandGroupById(int id) {
        for(IslandGroup island : islandGroups)
            if(island.getId() == id)
                return island;
        return null;
    }

    /**
     * method that joins two groups of islands together
     * @param islandGroupId1 identifier of the first group to join
     * @param islandGroupId2 identifier of the second group to join
     */
    public void Join(int islandGroupId1, int islandGroupId2) {
        IslandGroup island1 = getIslandGroupById(islandGroupId1);
        IslandGroup island2 = getIslandGroupById(islandGroupId2);

        island1.islandsNumber = island1.islandsNumber + island2.islandsNumber;
        for(TowerColor team : island1.getInfluence().keySet())
            island1.setInfluence(team, island1.getInfluence().get(team) + island2.getInfluence().get(team));
        if(island2.hasMotherNature)
            island1.hasMotherNature = true;
        if(island2.hasNoEntryTile)
            island1.hasNoEntryTile = true;

        island1.addStudents(island2.getStudents());

        islandGroups.remove(islandGroupId2);
        for(int i=islandGroupId2+1; i<islandGroups.size()+1; i++)
            getIslandGroupById(i).setId(i-1);
    }

    /**
     * method that changes the island on which mother nature is
     * @param islandId identifier of the island on which mother nature is moved
     */
    public void setMotherNatureIsland(int islandId){
        getMotherNatureIsland().hasMotherNature = false;
        getIslandGroupById(islandId).hasMotherNature = true;
    }

}
