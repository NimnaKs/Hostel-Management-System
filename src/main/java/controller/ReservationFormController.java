package controller;

import Bo.BOFactory;
import Bo.custom.ReservationBO;
import Bo.custom.RoomBO;
import Bo.custom.TenantBO;
import com.jfoenix.controls.JFXButton;
import dto.ReservationDto;
import dto.RoomDto;
import dto.TenantDto;
import entity.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tm.ReservationTm;
import tm.TenantTm;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReservationFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane root1;

    @FXML
    private TextField txtName;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton payNowBtn;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private ComboBox<String> tenantIdCmb;

    @FXML
    private TextField availableQty;

    @FXML
    private ComboBox<String> roomIdCmb;

    @FXML
    private TextField txtType;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField keyMoney;

    @FXML
    private JFXButton payingStatusBtn;

    @FXML
    private JFXButton bookNowBtn;

    @FXML
    private TableView<ReservationTm> tblReservation;

    @FXML
    private TableColumn<ReservationTm,String> colTenantId;

    @FXML
    private TableColumn<ReservationTm,String> colReservationId;

    @FXML
    private TableColumn<ReservationTm,String> colRoomId;

    @FXML
    private TableColumn<ReservationTm,String> colDate;

    @FXML
    private TableColumn<ReservationTm,JFXButton> colPayingStatus;

    @FXML
    private Label reservationId;

    private boolean updateReservation=false;

    ReservationBO reservationBO= (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    TenantBO tenantBO= (TenantBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TENANTS);

    RoomBO roomBO= (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOMS);

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if (!txtSearch.getText().isEmpty()) {
            ReservationDto reservationDto = reservationBO.viewReservation(txtSearch.getText());
            if (reservationDto!=null) {
                reservationId.setText(reservationDto.getRes_id());
                tenantIdCmb.getSelectionModel().select(reservationDto.getTenantDto().getStudent_id());
                txtName.setText(reservationDto.getTenantDto().getName());
                roomIdCmb.getSelectionModel().select(reservationDto.getRoomDto().getRoom_type_id());
                txtType.setText(reservationDto.getRoomDto().getType());
                availableQty.setText(String.valueOf(reservationDto.getRoomDto().getQty()-reservationBO.
                        getReservationCount(reservationDto.getRoomDto().getRoom_type_id())));
                txtDate.setValue(reservationDto.getDate().toLocalDate());
                keyMoney.setText(String.valueOf(reservationDto.getRoomDto().getKey_money()));
                if (reservationDto.getStatus().equals("Not Complete")){
                    payingStatusBtn.setText("Not Complete");
                    payingStatusBtn.setStyle("-fx-background-color:  red");
                    updateReservation=true;
                }else{
                    payingStatusBtn.setText("Paid Complete");
                    payingStatusBtn.setStyle("-fx-background-color:  #28DF99");
                    updateReservation=false;
                }
                txtSearch.setText(null);
                bookNowBtn.setText("Cancel");
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Invalid reservation Id ! Enter valid one.").show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Enter a reservation Id").show();
        }
    }

    @FXML
    void onActionBookNow(ActionEvent event) {
        try {
            if (!bookNowBtn.getText().equals("Cancel")) {
                if (validation()) {
                    ReservationDto reservation = new ReservationDto();
                    RoomDto roomDto = roomBO.viewRoom(roomIdCmb.getValue());
                    TenantDto tenantDto1 = new TenantDto();
                    tenantDto1.setStudent_id(tenantIdCmb.getValue());
                    TenantDto tenantDto = tenantBO.viewTenant(tenantDto1);
                    reservation.setRes_id(reservationId.getText());
                    reservation.setStatus("Not Complete");
                    reservation.setDate(Date.valueOf(txtDate.getValue()));
                    reservation.setTenantDto(tenantDto);
                    reservation.setRoomDto(roomDto);
                    reservationBO.saveReservation(reservation);
                    new Alert(Alert.AlertType.INFORMATION, "Book Added").show();
                    clearAll();
                    refreshTable();
                } else {
                    throw new RuntimeException("invalid input data in fields!");
                }
            }else{
                clearAll();
                refreshTable();
                bookNowBtn.setText("Book Now");
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onActionDelete(ActionEvent event) {
        try {
            if (!reservationId.getText().equals(reservationBO.getLastId())) {
                reservationBO.deleteReservation(reservationId.getText());
                new Alert(Alert.AlertType.INFORMATION, "Reservation Deleted").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Reservation first!").show();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearAll();
    }

    private void clearAll() {
        refreshTable();
        tenantIdCmb.getSelectionModel().select(null);
        txtName.clear();
        roomIdCmb.getSelectionModel().select(null);
        txtType.clear();
        availableQty.clear();
        txtDate.setValue(null);
        keyMoney.clear();
        payingStatusBtn.setText("Not Complete");
        payingStatusBtn.setStyle("-fx-background-color:  red");
    }

    private void refreshTable() {
        reservationId.setText(reservationBO.getLastId());
        try {
            List<ReservationDto> all = reservationBO.getAll();
            ObservableList<ReservationTm> observableList = FXCollections.observableArrayList();
            all.stream().map(dto -> observableList.add(new ReservationTm(dto.getRes_id(), dto.getDate(), dto.getStatus(), dto.getTenantDto().getStudent_id(), dto.getRoomDto().getRoom_type_id()))).collect(Collectors.toList());
            tblReservation.setItems(observableList);
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            tblReservation.getItems().clear();
        }
    }

    @FXML
    void onActionPayNow(ActionEvent event) {
        if (validation()) {
            ReservationDto reservation = new ReservationDto();
            RoomDto roomDto = roomBO.viewRoom(roomIdCmb.getValue());
            TenantDto tenantDto1 = new TenantDto();
            tenantDto1.setStudent_id(tenantIdCmb.getValue());
            TenantDto tenantDto = tenantBO.viewTenant(tenantDto1);
            reservation.setRes_id(reservationId.getText());
            reservation.setStatus("Paid Complete");
            reservation.setDate(Date.valueOf(txtDate.getValue()));
            reservation.setTenantDto(tenantDto);
            reservation.setRoomDto(roomDto);
            if (updateReservation) {
                reservationBO.updateReservation(reservation);
            } else if (payingStatusBtn.getText().equals("Paid Complete")) {
                new Alert(Alert.AlertType.INFORMATION, "Already paid completed !").show();
            }else{
                reservationBO.saveReservation(reservation);
            }
            new Alert(Alert.AlertType.INFORMATION, "Reservation Added").show();
            clearAll();
            refreshTable();
        }else{
            throw new RuntimeException("invalid input data in fields!");
        }
    }

    private boolean validation() {
        return txtName.getText()!=null && txtType.getText()!=null && txtDate.getValue()!=null;
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("res_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPayingStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTenantId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("room_id"));

        refreshTable();

        txtName.setDisable(true);
        txtType.setDisable(true);
        keyMoney.setDisable(true);
        availableQty.setDisable(true);

        setTenantCmb();
        setRoomCmb();
    }

    private void setRoomCmb() {
        List<RoomDto> all = roomBO.getAllRooms();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (RoomDto roomDto:all) {
            list.add(roomDto.getRoom_type_id());
        }
        roomIdCmb.setItems(list);
    }

    private void setTenantCmb() {
        List<TenantDto> all = tenantBO.getAllTenants();
        ObservableList<String> tenantIdList = FXCollections.observableArrayList();
        for (TenantDto tenantdto:all) {
            tenantIdList.add(tenantdto.getStudent_id());
        }
        tenantIdCmb.setItems(tenantIdList);
    }

    @FXML
    void onActionSetRoomDetails(ActionEvent event) {
        if (!roomIdCmb.getSelectionModel().isEmpty()) {
            /*RoomDto roomDto = new RoomDto();
            roomDto.setRoom_type_id(roomIdCmb.getValue());*/
            RoomDto roomDto1 = roomBO.viewRoom(roomIdCmb.getValue());
            if (roomDto1!=null) {
                txtType.setText(roomDto1.getType());
                keyMoney.setText(String.valueOf(roomDto1.getKey_money()));
                availableQty.setText(String.valueOf(roomDto1.getQty()-reservationBO.
                        getReservationCount(roomIdCmb.getValue())));
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Room Details Not Found !").show();
        }
    }

    @FXML
    void onActionSetTenantDetails(ActionEvent event) {
        if (!tenantIdCmb.getSelectionModel().isEmpty()) {
            TenantDto tenantDto = new TenantDto();
            tenantDto.setStudent_id(tenantIdCmb.getValue());
            TenantDto tenantDto1 = tenantBO.viewTenant(tenantDto);
            if (tenantDto1!=null) {
                txtName.setText(tenantDto1.getName());
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Tenant Details Not Found !").show();
        }
    }
}
