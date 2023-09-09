package controller;

import Bo.BOFactory;
import Bo.custom.TenantBO;
import Bo.custom.UserBO;
import com.jfoenix.controls.JFXButton;
import dto.TenantDto;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import util.regex.RegExFactory;
import util.regex.RegExType;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private ImageView profileImg;

    private byte[] imgArray;

    UserBO userBO= (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void onActionUpdate(ActionEvent event) {
        try {
            if (validation()) {
                    userBO.updateTenant(new UserDto(
                         txtUserName.getText(),
                         txtPassword.getText(),
                         txtName.getText(),
                         txtEmail.getText(),
                         imgArray
                    ));
                    new Alert(Alert.AlertType.INFORMATION, "Profile Details Updated Successfully").show();
            } else {
                throw new RuntimeException("invalid input data in fields!");
            }
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validation() {
        return RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtName.getText()).matches() &&
                RegExFactory.getInstance().getPattern(RegExType.EMAIL).matcher(txtEmail.getText()).matches() &&
                RegExFactory.getInstance().getPattern(RegExType.PASSWORD).matcher(txtPassword.getText()).matches() &&
                imgArray!=null && RegExFactory.getInstance().getPattern(RegExType.NAME).matcher(txtUserName.getText()).matches();
    }

    @FXML
    void onClickedChangeDp(MouseEvent event) throws IOException {

        String imgPath="/Users/mac/Desktop/Hostel-Management-System/src/main/resources/assets/add-user.png";

        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Stage primaryStage = (Stage) root.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            imgPath = selectedFile.getAbsolutePath();
        }

        File imageFile = new File(imgPath);
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        imgArray = new byte[(int) imageFile.length()];
        fileInputStream.read(imgArray);
        fileInputStream.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imgArray);
        Image image = new Image(byteArrayInputStream);
        profileImg.setImage(image);
    }
    @FXML
    void onMouseClick(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/userDetailsForm.fxml")));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.hide();
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserDto userDto=userBO.getUserDetails(LoginFormController.user_id);
        txtUserName.setText(userDto.getId());
        txtPassword.setText(userDto.getPassword());
        txtEmail.setText(userDto.getEmail());
        txtName.setText(userDto.getName());
        System.out.println(userDto.getName());
        ByteArrayInputStream byteArrayInputStream;
        if(userDto.getProPic()!=null) {
            imgArray=userDto.getProPic();
            byteArrayInputStream = new ByteArrayInputStream(imgArray);
        }else{
            String imgPath="/Users/mac/Desktop/Hostel-Management-System/src/main/resources/assets/add-user.png";
            File imageFile = new File(imgPath);
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            imgArray = new byte[(int) imageFile.length()];
            fileInputStream.read(imgArray);
            fileInputStream.close();
            byteArrayInputStream = new ByteArrayInputStream(imgArray);
        }
        Image image = new Image(byteArrayInputStream);
        profileImg.setImage(image);
    }
}
