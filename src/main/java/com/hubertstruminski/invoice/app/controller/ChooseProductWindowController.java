package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.service.ChooseCoreService;
import com.hubertstruminski.invoice.app.service.ChooseProductWindowService;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class ChooseProductWindowController implements FxmlController {

    private final ChooseProductWindowService chooseProductWindowService;
    private final ChooseCoreService chooseCoreService;

    @Autowired
    public ChooseProductWindowController(
            ChooseProductWindowService chooseProductWindowService,
            ChooseCoreService chooseCoreService) {
        this.chooseProductWindowService = chooseProductWindowService;
        this.chooseCoreService = chooseCoreService;
    }

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize() {
        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        Set<Product> productsBackup = chooseProductWindowService.findAndFilterProducts();

        for(Product product: productsBackup) {
            GridPane gridPane = chooseProductWindowService.createGridPaneStructure(product);
            VBox vBox = chooseCoreService.createVBox(gridPane);
            chooseProductWindowService.addEventHandlerForChooseCustomer(vBox, product, scrollPane);

            mainVBox.getChildren().add(vBox);
        }
    }
}
