package controller;

import Bo.BOFactory;
import Bo.custom.UserBO;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.regex.RegExFactory;
import util.regex.RegExType;

import java.io.IOException;
import java.util.Objects;

public class CreateAccountFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label title_bar;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField reEnterPasswordAgain;

    UserBO userBO= (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

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
        try {
            if (checkRegEx()) {
                UserDto userDto = new UserDto(username.getText(),password.getText(),
                        null,null,null);
                userBO.saveUserDetails(userDto);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration Success! ");
                alert.showAndWait();
                clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Invalid input or password are not match!").showAndWait();
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
        }
    }

    private void clear() {
        username.clear();
        password.clear();
        reEnterPasswordAgain.clear();
    }

    private boolean checkRegEx() throws RuntimeException {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(username.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.PASSWORD).matcher(password.getText()).matches() && password.getText().equals(reEnterPasswordAgain.getText());
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
