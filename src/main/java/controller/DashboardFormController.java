package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Label title_bar;

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton dashboard1;

    @FXML
    private JFXButton dashboard11;

    @FXML
    private JFXButton dashboard111;

    @FXML
    private JFXButton dashboard1111;

    @FXML
    private JFXButton dashboard11111;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    void onMouseClick(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboardForm.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create data series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("January", 5000));
        series.getData().add(new XYChart.Data<>("February", 6000));
        series.getData().add(new XYChart.Data<>("March", 7500));
        series.getData().add(new XYChart.Data<>("April", 8500));
        series.getData().add(new XYChart.Data<>("June", 500));
        series.getData().add(new XYChart.Data<>("July", 3500));
        series.getData().add(new XYChart.Data<>("August", 750));
        series.getData().add(new XYChart.Data<>("September", 1500));
        series.getData().add(new XYChart.Data<>("October", 2500));
        series.getData().add(new XYChart.Data<>("November", 6500));
        series.getData().add(new XYChart.Data<>("December", 7500));
        // Add more data for other months

        // Add series to the chart
        barChart.getData().add(series);
    }
}
