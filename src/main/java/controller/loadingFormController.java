package controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class loadingFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ProgressBar progressBar;

    private double xOffset = 0;
    private double yOffset = 0;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    updateProgress(i, 55);
                    Thread.sleep(100);
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> {
            try {
                Stage stage1 = (Stage) root.getScene().getWindow();
                stage1.close();
                Parent load = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/loginForm.fxml")));
                load.setOnMousePressed(this::handleMousePressed);
                load.setOnMouseDragged(this::handleMouseDragged);
                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        new Thread(task).start();
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
