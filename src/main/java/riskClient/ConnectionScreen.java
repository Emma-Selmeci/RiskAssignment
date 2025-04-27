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
    private Button connectButton;

    void setText(String text) {
        label.setText(text);
    }

    void connectionFailed() {
        connectButton.setDisable(false);
        label.setText("Failed to connect to server");
    }

    ConnectionScreen(ClientApplication app) {
        this.app = app;
        TextField address = new TextField("localhost");
        connectButton = new Button("Connect to server");
        label = new Label("...");

        connectButton.setOnAction(event -> {
            connectButton.setDisable(true);
            app.initiateConnection(address.getText());
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL);
        root.getChildren().add(connectButton);
        root.getChildren().add(address);
        root.getChildren().add(label);

        this.setCenter(root);

    }

}
