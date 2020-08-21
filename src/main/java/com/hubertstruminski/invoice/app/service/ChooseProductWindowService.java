package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.controller.NewInvoiceWindowController;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChooseProductWindowService {

    private final NewInvoiceWindowController newInvoiceWindowController;
    private final ProductRepository productRepository;

    @Autowired
    public ChooseProductWindowService(
            NewInvoiceWindowController newInvoiceWindowController,
            ProductRepository productRepository) {
        this.newInvoiceWindowController = newInvoiceWindowController;
        this.productRepository = productRepository;
    }

    public void addEventHandlerForChooseCustomer(VBox vBox, Product product, ScrollPane scrollPane) {
        vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            newInvoiceWindowController.setProduct(product);
            Stage stage = (Stage) scrollPane.getScene().getWindow();
            stage.close();
        });
    }

    public Set<Product> findAndFilterProducts() {
        Iterable<Product> productsIterable = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        productsIterable.forEach(products::add);

        List<Product> chosenProducts = newInvoiceWindowController.getChosenProducts();

        final Set<Product> productsBackup = new HashSet<>(products);
        products.removeAll(chosenProducts);
        chosenProducts.removeAll(productsBackup);
        return productsBackup;
    }

    public GridPane createGridPaneStructure(Product product) {
        GridPane gridPane = new GridPane();

        gridPane.add(new Label(product.getName()), 0, 0);
        gridPane.add(new Label("Ilość: " + product.getAmount()), 0 ,1);
        gridPane.add(new Label("Cena: " + product.getPrice() + Constants.CURRENCY), 0 ,2);

        return gridPane;
    }
}
