package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
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
import java.util.List;

@Controller
public class ChooseTaxWindowController implements FxmlController {

    private final TaxRepository taxRepository;
    private final NewProductWindowController newProductWindowController;

    @Autowired
    public ChooseTaxWindowController(
            @Lazy NewProductWindowController newProductWindowController,
            TaxRepository taxRepository) {
        this.newProductWindowController = newProductWindowController;
        this.taxRepository = taxRepository;
    }

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize() {
        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        Iterable<Tax> taxsIterable = taxRepository.findAll();
        List<Tax> taxs = new ArrayList<>();
        taxsIterable.forEach(taxs::add);

        for(Tax tax: taxs) {
            GridPane gridPane = new GridPane();

            gridPane.add(new Label(tax.getName()), 0, 0);
            gridPane.add(new Label(tax.getTaxAmount()), 0 ,1);

            VBox vBox = new VBox(gridPane);
            vBox.setFillWidth(true);

            vBox.setMaxWidth(400);
            vBox.setMinWidth(400);

            vBox.setStyle(Constants.CHOOSE_INTERFACE_ITEMS);

            vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                newProductWindowController.setTax(tax);
                Stage stage = (Stage) scrollPane.getScene().getWindow();
                stage.close();
            });
            mainVBox.getChildren().add(vBox);
        }
    }
}
