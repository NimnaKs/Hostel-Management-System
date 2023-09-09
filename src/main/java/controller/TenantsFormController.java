package controller;

import Bo.BOFactory;
import Bo.custom.TenantBO;
import com.jfoenix.controls.JFXButton;
import dto.TenantDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tm.TenantTm;
import util.FactoryConfiguration;
import util.regex.RegExFactory;
import util.regex.RegExType;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TenantsFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContact;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private ComboBox<String> genderCombobox;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TextField txtId;

    @FXML
    private AnchorPane root1;

    @FXML
    private TableView<TenantTm> tblTenants;

    @FXML
    private TableColumn<TenantTm,String> colTenantId;

    @FXML
    private TableColumn<TenantTm,String> colTenantName;

    @FXML
    private TableColumn<TenantTm,String> colContact;

    @FXML
    private TableColumn<TenantTm,String> colAddress;

    @FXML
    private TableColumn<TenantTm,String> colDob;

    TenantBO tenantBO= (TenantBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TENANTS);

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if (!txtSearch.getText().isEmpty()) {
            TenantDto tenantDto = new TenantDto();
            tenantDto.setStudent_id(txtSearch.getText());
            TenantDto tenantDto1 = tenantBO.viewTenant(tenantDto);
            if (tenantDto1!=null) {
                txtName.setText(tenantDto1.getName());
                txtId.setText(tenantDto1.getStudent_id());
                txtDOB.setValue(tenantDto1.getDob().toLocalDate());
                txtAddress.setText(tenantDto1.getAddress());
                txtContact.setText(tenantDto1.getContact_no());
                genderCombobox.getSelectionModel().select(tenantDto1.getGender());
                saveBtn.setText("Update");
                txtSearch.setText(null);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Invalid student Id ! Enter valid one.").show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Enter a student Id").show();
        }
    }

    @FXML
    void onActionDelete(ActionEvent event) {
        try {
            if (!txtId.equals(tenantBO.getLastId())) {
                tenantBO.deleteTenant(txtId.getText());
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted").show();
                refreshTable();
                clearAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Student first!").show();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void onActionSave(ActionEvent event) {
        try {
            if (validation()) {
                if (saveBtn.getText().equals("Save")) {
                    TenantDto tenantDto = new TenantDto();
                    tenantDto.setStudent_id(txtId.getText());
                    tenantDto.setName(txtName.getText());
                    tenantDto.setAddress(txtAddress.getText());
                    tenantDto.setGender(genderCombobox.getValue());
                    tenantDto.setContact_no(txtContact.getText());
                    tenantDto.setDob(Date.valueOf(txtDOB.getValue()));
                    tenantBO.saveTenant(tenantDto);
                    new Alert(Alert.AlertType.INFORMATION, "Student Added").show();
                }else{
                    TenantDto tenantDto = new TenantDto();
                    tenantDto.setStudent_id(txtId.getText());
                    tenantDto.setName(txtName.getText());
                    tenantDto.setAddress(txtAddress.getText());
                    tenantDto.setGender(genderCombobox.getValue());
                    tenantDto.setContact_no(txtContact.getText());
                    tenantDto.setDob(Date.valueOf(txtDOB.getValue()));
                    tenantBO.updateTenant(tenantDto);
                    new Alert(Alert.AlertType.INFORMATION, "Student Updated").show();
                }
                clearAll();
                refreshTable();
                saveBtn.setText("Save");
            } else {
                throw new RuntimeException("invalid input data in fields!");
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void refreshTable() {
        try {
            generateStudentId();
            List<TenantDto> all = tenantBO.getAllTenants();
            ObservableList<TenantTm> studentDtoObservableList = FXCollections.observableArrayList();
            all.forEach(dto -> studentDtoObservableList.add(new TenantTm(dto.getStudent_id(), dto.getName(), dto.getAddress(), dto.getContact_no(), dto.getDob(), dto.getGender())));
            tblTenants.setItems(studentDtoObservableList);
        } catch (RuntimeException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
            tblTenants.getItems().clear();
        }
    }

    private void generateStudentId() {
        txtId.setText(tenantBO.getLastId());
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    private void clearAll() {
        txtContact.clear();
        txtAddress.clear();
        txtContact.clear();
        txtName.clear();
        genderCombobox.getSelectionModel().select(null);
        txtDOB.setValue(null);
    }

    private boolean validation() {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtName.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.CITY).matcher(txtAddress.getText()).matches() && RegExFactory.getInstance().getPattern(RegExType.MOBILE).matcher(txtContact.getText()).matches() && txtDOB.getValue() != null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGenderComboBox();

        colTenantId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colTenantName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        refreshTable();

        txtId.setDisable(true);

    }

    private void setGenderComboBox() {
        ObservableList<String> genderList = FXCollections.observableArrayList();

        genderList.addAll("Male","Female");

        genderCombobox.setItems(genderList);
    }
}
