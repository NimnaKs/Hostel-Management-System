package controller;

import Bo.BOFactory;
import Bo.custom.UserBO;
import com.jfoenix.controls.JFXButton;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Label title_bar;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private JFXButton createAccount;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private Label forgetPassword;

    private double xOffset = 0;
    private double yOffset = 0;

    public static String user_id;

    UserBO userBO= (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void onMouseClick(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/loginForm.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onActionCreateAccount(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) root.getScene().getWindow();
        stage1.close();
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/createAccountForm.fxml")));
        load.setOnMousePressed(this::handleMousePressed);
        load.setOnMouseDragged(this::handleMouseDragged);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void onActionForgetPassword(MouseEvent event) throws IOException {
        Stage stage1 = (Stage) root.getScene().getWindow();
        stage1.close();
        Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/otpForm.fxml")));
        load.setOnMousePressed(this::handleMousePressed);
        load.setOnMouseDragged(this::handleMouseDragged);
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        if (checkRegEx()) {
            try {
                UserDto user = userBO.viewCredentials(username.getText());
                if (user.getId().equals(username.getText()) && user.getPassword().equals(password.getText())) {
                    Stage stage1 = (Stage) root.getScene().getWindow();
                    stage1.close();
                    Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboardForm.fxml")));
                    load.setOnMousePressed(this::handleMousePressed);
                    load.setOnMouseDragged(this::handleMouseDragged);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(load));
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.centerOnScreen();
                    stage.show();
                    user_id=user.getId();
                }else{
                    new Alert(Alert.AlertType.INFORMATION, "Incorrect password or user name!").show();
                }
            } catch (RuntimeException | IOException | SQLException | ClassNotFoundException exception) {
                new Alert(Alert.AlertType.INFORMATION, exception.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Invalid Input!").show();
        }
    }

    private boolean checkRegEx() {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(username.getText()).matches() && RegExFactory.
                getInstance().getPattern(RegExType.PASSWORD).matcher(password.getText()).matches();
    }

    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        Stage primaryStage = (Stage) ((Parent) event.getSource()).getScene().getWindow();
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
    }
}
