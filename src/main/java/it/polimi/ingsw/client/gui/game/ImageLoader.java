package it.polimi.ingsw.client.gui.game;

import javafx.scene.image.Image;

public class ImageLoader {
    private Image island_1, island_2, island_3;


    public ImageLoader() {
        load();
    }

    private void load(){
        island_1 = new Image("game/graphics/board/island_1.png");
        island_2 = new Image("game/graphics/board/island_2.png");
        island_3 = new Image("game/graphics/board/island_3.png");
    }

    public Image getIsland_1() {
        return island_1;
    }
    public Image getIsland_2() {
        return island_2;
    }
    public Image getIsland_3() {
        return island_3;
    }
}
