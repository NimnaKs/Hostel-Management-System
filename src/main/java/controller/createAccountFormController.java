package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class createAccountFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label title_bar;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField reEnterPasswordAgain;

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) root.getScene().getWindow();
        stage1.close();
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/loginForm.fxml")));
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

    @FXML
    void onMouseClick(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/createAccountForm.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.hide();
    }

}
