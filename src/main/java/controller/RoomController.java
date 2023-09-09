package controller;

import Bo.BOFactory;
import Bo.custom.RoomBO;
import com.jfoenix.controls.JFXButton;
import dto.RoomDto;
import dto.TenantDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tm.ReservationTm;
import tm.RoomTm;
import util.regex.RegExFactory;
import util.regex.RegExType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomController implements Initializable {

    @FXML
    private AnchorPane root1;

    @FXML
    private TextField txtType;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtKeyMoney;

    @FXML
    private TableView<RoomTm> tblRooms;

    @FXML
    private TableColumn<ReservationTm,String> colRoomTypeId;

    @FXML
    private TableColumn<ReservationTm,String> colRoomType;

    @FXML
    private TableColumn<ReservationTm,Double> colKeyMoney;

    @FXML
    private TableColumn<ReservationTm,Integer> colQty;

    @FXML
    private ComboBox<String> typeCombobox;

    RoomBO roomBO= (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOMS);

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if (!txtSearch.getText().isEmpty()) {
            RoomDto room = roomBO.viewRoom(txtSearch.getText());
            if (room!=null) {
                typeCombobox.getSelectionModel().select(room.getType());
                txtId.setText(room.getRoom_type_id());
                txtKeyMoney.setText(String.valueOf(room.getKey_money()));
                txtQty.setText(String.valueOf(room.getQty()));
                saveBtn.setText("Update");
                txtSearch.setText(null);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Invalid Room Id ! Enter valid one.").show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Enter a Room Id").show();
        }
    }

    @FXML
    void onActionDelete(ActionEvent event) {
        try {
            if (!txtId.getText().equals(roomBO.generateNextId()) || txtId.getText()!=null) {
                roomBO.deleteRoom(txtId.getText());
                new Alert(Alert.AlertType.INFORMATION, "Room Deleted").show();
                refreshTable();
            } else {
                throw new RuntimeException("Select Room First");
            }
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
        clearAll();
        saveBtn.setText("Save");
        refreshTable();
    }

    @FXML
    void onActionSave(ActionEvent event) {
        try {
            if (validateData()) {
                if (saveBtn.getText().equals("Save")) {
                    RoomDto roomDto = new RoomDto();
                    roomDto.setRoom_type_id(txtId.getText());
                    roomDto.setType(typeCombobox.getValue());
                    roomDto.setKey_money(Double.valueOf(txtKeyMoney.getText()));
                    roomDto.setQty(Integer.valueOf(txtQty.getText()));
                    roomBO.saveRoom(roomDto);
                    new Alert(Alert.AlertType.INFORMATION, "Room Added").show();
                }else{
                    RoomDto roomDto = new RoomDto();
                    roomDto.setRoom_type_id(txtId.getText());
                    roomDto.setType(typeCombobox.getValue());
                    roomDto.setKey_money(Double.valueOf(txtKeyMoney.getText()));
                    roomDto.setQty(Integer.valueOf(txtQty.getText()));
                    saveBtn.setText("Save");
                    roomBO.updateRoom(roomDto);
                    new Alert(Alert.AlertType.INFORMATION, "Room Updated").show();
                }
                refreshTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
            }
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
        clearAll();
    }

    private void refreshTable() {
        try {
            txtId.setText(roomBO.generateNextId());
            List<RoomDto> all = roomBO.getAllRooms();
            ObservableList<RoomTm> roomObservableList = FXCollections.observableArrayList();
            all.forEach(dto -> roomObservableList.add(new RoomTm(dto.getRoom_type_id(), dto.getType(), dto.getKey_money(), dto.getQty())));
            tblRooms.setItems(roomObservableList);
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            tblRooms.getItems().clear();
        }
    }

    private void clearAll() {
        txtQty.clear();
        txtKeyMoney.clear();
        typeCombobox.getSelectionModel().select(null);
    }

    private boolean validateData() {
        return RegExFactory.getInstance().getPattern(RegExType.DOUBLE).matcher(txtKeyMoney.getText()).matches() /*&& RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtType.getText()).matches()*/ && RegExFactory.getInstance().getPattern(RegExType.INTEGER).matcher(txtQty.getText()).matches();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        refreshTable();

        txtId.setDisable(true);

        setComboBox();
    }

    private void setComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        typeList.addAll("A/C","A/C-Food","Non A/C","Non A/C-Food");

        typeCombobox.setItems(typeList);
    }
}
