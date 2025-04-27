module risk.risk {
    requires javafx.controls;
    requires javafx.fxml;


    opens risk to javafx.fxml;
    exports risk;
    exports riskClient;
}