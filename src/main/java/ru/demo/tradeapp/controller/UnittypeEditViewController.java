package ru.demo.tradeapp.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.demo.tradeapp.model.*;
import ru.demo.tradeapp.service.*;
import ru.demo.tradeapp.util.Manager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import static ru.demo.tradeapp.util.Manager.MessageBox;

public class UnittypeEditViewController implements Initializable {
    private UnittypeService unittypeService = new UnittypeService();
    @FXML
    private Button BtnCancel;

    @FXML
    private Button BtnSave;








    @FXML
    private TextField TextFieldTitle;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (Manager.currentUnittype != null) {

            TextFieldTitle.setText(Manager.currentUnittype.getTitle());

        } else {
            Manager.currentUnittype = new Unittype();
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

        Manager.currentUnittype.setTitle(TextFieldTitle.getText());


        if (Manager.currentUnittype.getUnittypeId() == null) {

            unittypeService.save(Manager.currentUnittype);
            MessageBox("Информация", "", "Данные сохранены успешно", Alert.AlertType.INFORMATION);
        } else {
            unittypeService.update(Manager.currentUnittype);
            MessageBox("Информация", "", "Данные обновлены успешно", Alert.AlertType.INFORMATION);
        }
    }

    StringBuilder checkFields() {
        StringBuilder error = new StringBuilder();
        if (TextFieldTitle.getText().isEmpty()|| TextFieldTitle.getText().length() > 30) {
            error.append("Укажите название единицы или уменьшите длину текста\n");
        }


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
