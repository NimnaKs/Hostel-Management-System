package controller;

import Bo.BOFactory;
import Bo.custom.ReservationBO;
import Bo.custom.RoomBO;
import Bo.custom.TenantBO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardFormPanelController implements Initializable {

    @FXML
    private AnchorPane root1;

    @FXML
    private Label totalRooms;

    @FXML
    private Label availableRooms;

    @FXML
    private Label totalTenants;

    @FXML
    private Label totalIncome;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private Label time;

    @FXML
    private Label date;

    RoomBO roomBO= (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOMS);

    ReservationBO reservationBO= (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    TenantBO tenantBO= (TenantBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TENANTS);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setText(new SimpleDateFormat("dd:MM:20yy").format(new Date()));
        Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(0), event->time.setText(new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime()))), (KeyFrame) new KeyFrame(Duration.seconds(1))); timeline.setCycleCount(Animation.INDEFINITE);timeline.play();
        setRoomsCount();
        setAvailableRoomCount();
        setTenantsCount();
        setIncome();
    }

    private void setIncome() {
        /*totalIncome.setText(reservationBO.getIncome());*/
    }

    private void setTenantsCount() {
        totalTenants.setText(tenantBO.getTenantsCount());
    }

    private void setAvailableRoomCount() {
        availableRooms.setText(String.valueOf(Integer.parseInt(roomBO.getRoomQty())-Integer.parseInt(reservationBO.getUnfreeRooms())));
    }

    private void setRoomsCount() {
        totalRooms.setText(roomBO.getRoomQty());
    }


}
