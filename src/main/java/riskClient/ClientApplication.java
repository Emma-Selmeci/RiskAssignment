package riskClient;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    ClientManager manager;
    private ConnectionScreen connectionScreen;

    void initiateConnection(String host) {
        new Thread(new ClientConnector(host,this, manager)).start(); //Will simply return in case of a fail
    }

    void setLabel(final String text) {
        connectionScreen.setText(text);
    }

    void connectionFailed() {
        connectionScreen.connectionFailed();
    }

    void connectionSuccesful() {
        connectionScreen.setText("Connection established with server");
    }

    @Override
    public void start(Stage stage) throws IOException {
        manager = new ClientManager();
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