module risk.risk {
    requires javafx.controls;
    requires javafx.fxml;


    opens risk.risk to javafx.fxml;
    exports risk.risk;
}