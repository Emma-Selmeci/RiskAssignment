package riskClient;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

class ConnectionScreen extends BorderPane {

    private ClientApplication app;
    private Label label;
    private Label numLabel = new Label();
    private Button readyButton;
    private Button connectButton;
    private FlowPane root;
    private boolean readyButtonAdded = false;

    void setText(String text) {
        label.setText(text);
    }

    void connectionFailed() {
        connectButton.setDisable(false);
        label.setText("Failed to connect to server");
    }

    void connectionEstablished() {
        label.setText("Connection established with server");
        root.getChildren().add(numLabel);
    }

    void setNumPlayers(int i) {
        numLabel.setText("Number of players waiting : " + i);
        readyButton.setDisable(false);
        if(!readyButtonAdded && i > 1) {
            if(i > 1) root.getChildren().add(readyButton);
        }
    }

    ConnectionScreen(ClientApplication app) {
        this.app = app;
        TextField address = new TextField("localhost");
        connectButton = new Button("Connect to server");
        label = new Label("...");

        readyButton = new Button("READY");
        readyButton.setOnAction(event -> {
            readyButton.setDisable(true);
            app.ready();
        });

        connectButton.setOnAction(event -> {
            connectButton.setDisable(true);
            app.initiateConnection(address.getText());
        });

        root = new FlowPane(Orientation.VERTICAL);
        root.getChildren().add(connectButton);
        root.getChildren().add(address);
        root.getChildren().add(label);

        this.setCenter(root);

    }

}
