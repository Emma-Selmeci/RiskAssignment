package riskClient;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientManager {
    private ClientApplication app;
    private PrintWriter serverOutput;
    /**
     * Constructor creates an empty ClientManager object - used to avoid caching during initialization
     */
    ClientManager() {

    }

    void establish(ClientApplication app, BufferedReader serverInput, PrintWriter serverOutput) {
        this.app = app;
        this.serverOutput = serverOutput;
        Platform.runLater(() -> app.connectionSuccesful());
        new Thread(new ClientListener(serverInput,this)).start();
    }

    void sendLine(String line) {
        System.out.println("Setting app " + line);
        Platform.runLater(() -> app.setLabel(line));
    }

}
