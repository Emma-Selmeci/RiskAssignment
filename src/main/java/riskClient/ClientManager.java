package riskClient;

import javafx.application.Platform;
import riskShared.GameState;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientManager {
    private ClientApplication app;
    private PrintWriter serverOutput;
    private int id = -1;
    private GameState state = GameState.INITIALIZATION;

    /**
     * Constructor creates an empty ClientManager object - used to avoid caching during initialization
     */
    ClientManager() {}

    private void send(String line) {
        serverOutput.print(line + "\r\n");
        serverOutput.flush();
    }

    GameState getState() {
        return state;
    }

    void establish(ClientApplication app, BufferedReader serverInput, PrintWriter serverOutput) {
        this.app = app;
        this.serverOutput = serverOutput;
        Platform.runLater(() -> app.connectionSuccesful());
        new Thread(new ClientListener(serverInput,this)).start();
    }

    void numPlayers(int num) {
        if(id == -1) id = num-1;
        Platform.runLater(() -> app.setNumPlayers(num));
    }

    void ready() {
        send("r" + id);
    }

}
