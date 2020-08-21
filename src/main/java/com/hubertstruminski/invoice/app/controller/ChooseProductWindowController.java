package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ChooseProductWindowController implements FxmlController {

    private final ProductRepository productRepository;
    private final NewInvoiceWindowController newInvoiceWindowController;

    @Autowired
    public ChooseProductWindowController(
            @Lazy NewInvoiceWindowController newInvoiceWindowController,
            ProductRepository productRepository) {
        this.newInvoiceWindowController = newInvoiceWindowController;
        this.productRepository = productRepository;
    }

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize() {
        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        Iterable<Product> productsIterable = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        productsIterable.forEach(products::add);

        List<Product> chosenProducts = newInvoiceWindowController.getChosenProducts();

        final Set<Product> productsBackup = new HashSet<>(products);
        products.removeAll(chosenProducts);
        chosenProducts.removeAll(productsBackup);

        for(Product product: productsBackup) {
            GridPane gridPane = new GridPane();

            gridPane.add(new Label(product.getName()), 0, 0);
            gridPane.add(new Label("Ilość: " + product.getAmount()), 0 ,1);
            gridPane.add(new Label("Cena: " + product.getPrice() + Constants.CURRENCY), 0 ,2);

            VBox vBox = new VBox(gridPane);
            vBox.setFillWidth(true);

            vBox.setMaxWidth(400);
            vBox.setMinWidth(400);

            vBox.setStyle(Constants.CHOOSE_INTERFACE_ITEMS);

            vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                newInvoiceWindowController.setProduct(product);
                Stage stage = (Stage) scrollPane.getScene().getWindow();
                stage.close();
            });
            mainVBox.getChildren().add(vBox);
        }
    }
}
