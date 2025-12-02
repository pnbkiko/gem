package ru.demo.tradeapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ru.demo.tradeapp.model.Product;

import java.io.IOException;

public class ListCellController {

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView ImageViewPhoto;

    @FXML
    private Label LabelDescription;

    @FXML
    private Label LabelManufacturer;

    @FXML
    private TextFlow Price;

    @FXML
    private Label LabelPercent;

    @FXML
    private Label LabelTitle;



    public void setProduct(Product product) throws IOException {

        ImageViewPhoto.setImage(product.getPhoto());

        LabelTitle.setText(product.getTitle());
        LabelDescription.setText(product.getDescription());
        LabelManufacturer.setText(product.getManufacturer().getTitle());
        int Percent = product.getDiscountAmount();


        if (Percent>1) {
            if(Percent >= 15){
                gridPane.setStyle("-fx-background-color: #7fff00;");
            }
            else {

                gridPane.setStyle("-fx-background-color: #ff;");
            }
            double discountedPrice = product.getPriceWithDiscount();

            Text originalPrice = new Text(String.format("%.2f$ ", product.getCost()));
            originalPrice.setStrikethrough(true);

            Text discountedPriceText = new Text(String.format("%.2f$", discountedPrice));
            discountedPriceText.setFill(javafx.scene.paint.Color.RED);

            Price.getChildren().setAll(originalPrice, discountedPriceText);

            LabelPercent.setText(Percent + "%");
        }
        else{
            Text PriceText = new Text(String.format("%.2f$", product.getCost()));
            Price.getChildren().setAll(PriceText);
            gridPane.setStyle("-fx-background-color: #ff;");
        }

    }

}

