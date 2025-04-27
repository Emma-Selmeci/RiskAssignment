package riskClient;

import javafx.application.Platform;
import riskServer.RiskServer;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.io.IOException;

/**
 * The ClientConnector class establishes connection with the server and creates the I/O buffers
 */

public class ClientConnector implements Runnable {
    private ClientApplication app;
    private ClientManager manager;
    private String host;

    public ClientConnector(String host, ClientApplication app, ClientManager manager) {
        this.app = app;
        this.host = host;
        this.manager = manager;
    }

    public void run() {
        Socket clientSocket;
        try {
            clientSocket = new Socket(host, RiskServer.PORT_NUMBER);
        } catch (IOException e) {
            Platform.runLater(() -> app.connectionFailed());
            return;
        }

        try {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter serverOutput = new PrintWriter(clientSocket.getOutputStream());

            manager.establish(app,serverInput, serverOutput);
        } catch (IOException e) {
            Platform.runLater(() -> app.connectionFailed());
            try {
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
