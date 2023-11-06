package it.polimi.ingsw.client.views;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.gui.App;
import javafx.application.Platform;

public class LoadingView extends View {
    @Override
    public void run() {
        if(!Client.isGuiMode) {
            writeLine("Loading...");
            while (!super.mustStop()) {
                write("");
            }
            clearAll();
        }
    }
}
