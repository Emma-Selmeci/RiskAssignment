package riskClient;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    final ClientManager manager = new ClientManager();
    private ConnectionScreen connectionScreen;

    void initiateConnection(String host) {
        new Thread(new ClientConnector(host,this, manager)).start(); //Will simply return in case of a fail
    }

    void ready() {
        synchronized(manager) {
            manager.ready();
        }
    }

    void connectionFailed() {
        connectionScreen.connectionFailed();
    }

    void connectionSuccesful() {
        connectionScreen.connectionEstablished();
    }

    void setNumPlayers(int i) {
        connectionScreen.setNumPlayers(i);
    }

    @Override
    public void start(Stage stage) throws IOException {
        connectionScreen = new ConnectionScreen(this);

        Group activePanel = new Group(connectionScreen);

        Scene scene = new Scene(activePanel, 400, 300);

        stage.setTitle("Risk");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}