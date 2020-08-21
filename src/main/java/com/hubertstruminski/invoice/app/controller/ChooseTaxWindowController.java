package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.service.ChooseCoreService;
import com.hubertstruminski.invoice.app.service.ChooseTaxWindowService;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChooseTaxWindowController implements FxmlController {

    private final ChooseTaxWindowService chooseTaxWindowService;
    private final ChooseCoreService chooseCoreService;

    @Autowired
    public ChooseTaxWindowController(
            ChooseTaxWindowService chooseTaxWindowService,
            ChooseCoreService chooseCoreService) {
        this.chooseTaxWindowService = chooseTaxWindowService;
        this.chooseCoreService = chooseCoreService;
    }

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize() {
        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        List<Tax> taxes = chooseTaxWindowService.findTaxes();

        for(Tax tax: taxes) {
            GridPane gridPane = chooseTaxWindowService.createGridPaneStructure(tax);
            VBox vBox = chooseCoreService.createVBox(gridPane);
            chooseTaxWindowService.addEventHandlerForChooseTaxes(vBox, tax, scrollPane);

            mainVBox.getChildren().add(vBox);
        }
    }
}
