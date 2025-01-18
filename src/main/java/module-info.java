module pl.tcs.tcschess {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jshell;
    requires java.desktop;

    opens pl.tcs.tcschess to javafx.fxml;
    exports pl.tcs.tcschess;
    exports pl.tcs.tcschess.scenes;
    opens pl.tcs.tcschess.scenes to javafx.fxml;
}