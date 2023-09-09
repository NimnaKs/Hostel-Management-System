package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane root1;

    @FXML
    private Label title_bar;

    @FXML
    private JFXButton dashboardBtn;

    @FXML
    private JFXButton roomsBtn;

    @FXML
    private JFXButton tenantsBtn;

    @FXML
    private JFXButton reservationBtn;

    @FXML
    private JFXButton userDetailsBtn;

    @FXML
    private JFXButton signOutBtn;

    private AnchorPane anchorPane;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void onActionClickDashBoard(ActionEvent event) throws IOException {
        setForms("/view/dashboardFormControllerPanel.fxml");
    }

    @FXML
    void onActionClickRoom(ActionEvent event) throws IOException {
        setForms("/view/roomForm.fxml");
    }

    @FXML
    void onActionClickSignOut(ActionEvent event) throws IOException {
        AnchorPane anchorPane1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/loginForm.fxml")));
        anchorPane1.setOnMousePressed(this::handleMousePressed);
        anchorPane1.setOnMouseDragged(this::handleMouseDragged);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(anchorPane1));
        Stage stage1 = (Stage) root.getScene().getWindow();
        stage1.close();
        stage.show();
    }

    @FXML
    void onActionClickTenants(ActionEvent event) throws IOException {
        setForms("/view/tenantsForm.fxml");
    }

    @FXML
    void onActionReservation(ActionEvent event) throws IOException {
        setForms("/view/reservationForm.fxml");
    }

    @FXML
    void onActionUserDetails(ActionEvent event) throws IOException {
        AnchorPane anchorPane1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/userDetailsForm.fxml")));
        anchorPane1.setOnMousePressed(this::handleMousePressed);
        anchorPane1.setOnMouseDragged(this::handleMouseDragged);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(anchorPane1));
        stage.show();
    }

    @FXML
    void onMouseClick(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboardForm.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.hide();
    }

    public void setForms(String forms) throws IOException {

        String[] formsArray = {"/view/dashboardFormControllerPanel.fxml","/view/roomForm.fxml","/view/tenantsForm.fxml","/view/reservationForm.fxml"};

        JFXButton[] btnArray = {dashboardBtn,roomsBtn,tenantsBtn,reservationBtn};

        anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(forms)));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);

        for (int i = 0; i < formsArray.length; i++) {

            btnArray[i].setStyle("-fx-background-color:  #ececec");

            if (forms.equals(formsArray[i])) {
                btnArray[i].setStyle("-fx-background-color: #8dc6ff");
            }
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setForms("/view/dashboardFormControllerPanel.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
