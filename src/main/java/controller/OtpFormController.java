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

public class OtpFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label title_bar;

    @FXML
    private Label forgetPassword;

    @FXML
    private TextField otptxt;

    private double xOffset = 0;
    private double yOffset = 0;

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
    void onActionOtpConfirm(ActionEvent event) throws IOException {
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
    void onActionResendOTP(MouseEvent event) {

    }

    @FXML
    void onMouseClick(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/otpForm.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.hide();
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
