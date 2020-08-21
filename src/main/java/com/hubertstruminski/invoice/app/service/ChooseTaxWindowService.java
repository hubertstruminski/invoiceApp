package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.controller.NewProductWindowController;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChooseTaxWindowService {

    private final NewProductWindowController newProductWindowController;
    private final TaxRepository taxRepository;

    @Autowired
    public ChooseTaxWindowService(
            NewProductWindowController newProductWindowController,
            TaxRepository taxRepository) {
        this.newProductWindowController = newProductWindowController;
        this.taxRepository = taxRepository;
    }

    public void addEventHandlerForChooseTaxes(VBox vBox, Tax tax, ScrollPane scrollPane) {
        vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            newProductWindowController.setTax(tax);
            Stage stage = (Stage) scrollPane.getScene().getWindow();
            stage.close();
        });
    }

    public List<Tax> findTaxes() {
        Iterable<Tax> taxsIterable = taxRepository.findAll();
        List<Tax> taxs = new ArrayList<>();
        taxsIterable.forEach(taxs::add);
        return taxs;
    }

    public GridPane createGridPaneStructure(Tax tax) {
        GridPane gridPane = new GridPane();

        gridPane.add(new Label(tax.getName()), 0, 0);
        gridPane.add(new Label(tax.getTaxAmount()), 0 ,1);
        return gridPane;
    }
}
