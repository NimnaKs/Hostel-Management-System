package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class UserDetailsController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField password;

    @FXML
    private JFXButton updateBtn;

    @FXML
    void onActionUpdate(ActionEvent event) {

    }

    @FXML
    void onClickedChangeDp(MouseEvent event) {

    }

    @FXML
    void onMouseClick(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/userDetailsForm.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.hide();
    }

}
