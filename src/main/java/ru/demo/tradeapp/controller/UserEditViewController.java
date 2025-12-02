package ru.demo.tradeapp.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.demo.tradeapp.model.*;
import ru.demo.tradeapp.model.User;
import ru.demo.tradeapp.service.*;
import ru.demo.tradeapp.util.Manager;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ru.demo.tradeapp.util.Manager.MessageBox;

public class UserEditViewController implements Initializable {
    private UserService userService = new UserService();


    @FXML
    private Button BtnCancel;

    @FXML
    private Button BtnSave;

    @FXML
    private ComboBox<Long> ComboBoxRole;







    @FXML
    private TextField TextFieldFirstName;
    @FXML
    private TextField TextFieldSecondName;
    @FXML
    private TextField TextFieldMiddleName;
    @FXML
    private TextField TextFieldUsername;
    @FXML
    private TextField TextFieldPassword;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ComboBoxRole.setItems(FXCollections.observableArrayList(1l,2l,3l));

        if (Manager.currentUser != null) {

            TextFieldFirstName.setText(Manager.currentUser.getFirstName());
            TextFieldSecondName.setText(Manager.currentUser.getSecondName());
            TextFieldMiddleName.setText(Manager.currentUser.getMiddleName());
            TextFieldUsername.setText(Manager.currentUser.getUsername());
            TextFieldPassword.setText(Manager.currentUser.getPassword());
            ComboBoxRole.setValue(Manager.currentUser.getRoleId());


        } else {
            Manager.currentUser = new User();
        }
    }

    @FXML
    void BtnCancelAction(ActionEvent event) {
        Stage stage = (Stage) BtnCancel.getScene().getWindow();

        // do what you have to do
        stage.close();
    }

    @FXML
    void BtnSaveAction(ActionEvent event) throws IOException {
        String error = checkFields().toString();
        if (!error.isEmpty()) {
            MessageBox("Ошибка", "Заполните поля или уменьшите длину строк", error, Alert.AlertType.ERROR);
            return;
        }




        // Обновляем поля
        Manager.currentUser.setFirstName(TextFieldFirstName.getText());
        Manager.currentUser.setSecondName(TextFieldSecondName.getText());
        Manager.currentUser.setMiddleName(TextFieldMiddleName.getText());
        Manager.currentUser.setPassword(TextFieldPassword.getText());
        Manager.currentUser.setUsername(TextFieldUsername.getText());
        Manager.currentUser.setRoleId(ComboBoxRole.getValue());

        // Сохраняем или обновляем в базе
        if (Manager.currentUser.getUsername().isEmpty() || Manager.currentUser.getUsername()  ==  null) {

            userService.save(Manager.currentUser);

            MessageBox("Информация", "", "Данные обновлены успешно", Alert.AlertType.INFORMATION);
        } else {

            userService.update(Manager.currentUser);
            MessageBox("Информация", "", "Данные сохранены успешно", Alert.AlertType.INFORMATION);
        }
    }
    StringBuilder checkFields() {
        StringBuilder error = new StringBuilder();
//        if (TextFieldTitle.getText().isEmpty()|| TextFieldTitle.getText().length() > 30) {
//            error.append("Укажите название единицы или уменьшите длину текста\n");
//        }


        return error;
    }

    boolean IsInteger(String number) {
        if (number == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    boolean IsDouble(String number) {
        if (number == null) {
            return false;
        }
        try {
            number = number.replace(',', '.');
            double d = Double.parseDouble(number);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
