package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.service.ChooseCoreService;
import com.hubertstruminski.invoice.app.service.ChooseCustomerWindowService;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.util.List;

@Controller
public class ChooseCustomerWindowController implements FxmlController {

    private final ChooseCustomerWindowService chooseCustomerWindowService;
    private final ChooseCoreService chooseCoreService;

    @Autowired
    public ChooseCustomerWindowController(
            ChooseCustomerWindowService chooseCustomerWindowService,
            ChooseCoreService chooseCoreService) {
        this.chooseCustomerWindowService = chooseCustomerWindowService;
        this.chooseCoreService = chooseCoreService;
    }

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize() {
        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        List<Customer> customers = chooseCustomerWindowService.findCustomers();

        for(Customer customer: customers) {
            GridPane gridPane = chooseCustomerWindowService.createGridPaneStructure(customer);
            VBox vBox = chooseCoreService.createVBox(gridPane);

            chooseCustomerWindowService.addEventHandlerForChooseCustomer(vBox, customer, scrollPane);
            mainVBox.getChildren().add(vBox);
        }
    }
}
