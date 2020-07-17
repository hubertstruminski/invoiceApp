package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.NewTaxWindowComponent;
import com.hubertstruminski.invoice.app.fx.manager.MainWindowUiManager;
import com.hubertstruminski.invoice.app.fx.manager.NewTaxWindowUiManager;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import com.hubertstruminski.invoice.app.service.TaxService;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.model.fxml.FxmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class NewTaxWindowController implements FxmlController {

    private Stage stage;

//    @Autowired
//    private TaxRepository taxRepository;

    @Autowired
    private TaxService taxService;

    @Autowired
    private ViewCreator viewCreator;

    @Autowired
    private EasyFxml easyFxml;

    @Autowired
    private MainWindowUiManager mainWindowUiManager;

    @Autowired
    private NewTaxWindowComponent newTaxWindowComponent;

    @Autowired
    private NewTaxWindowUiManager newTaxWindowUiManager;

    @FXML
    private VBox vBox;

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label taxAmountLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField taxAmountTextField;

    @FXML
    public Button newTaxSaveButton;

    @FXML
    void onNewTaxSaveButtonAction(ActionEvent event) {
        Tax tax = new Tax();
        tax.setName(nameTextField.getText());
        tax.setDescription(descriptionTextField.getText());
        tax.setTaxAmount(taxAmountTextField.getText());

        taxService.save(tax);
    }

    @Override
    public void initialize() {
        System.out.println("Hubert");
    }

    public Stage initStage() {
//        Scene scene1 = newTaxWindowUiManager.getScene(newTaxWindowComponent);
        Scene scene = newTaxSaveButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        return stage;
    }
}
