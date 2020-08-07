package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.*;
import com.hubertstruminski.invoice.app.service.MainWindowService;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;

import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainWindowController implements FxmlController {

    private final TaxWindowComponent taxWindowComponent;
    private final NewTaxWindowComponent newTaxWindowComponent;
    private final MainWindowService mainWindowService;
    private final NewCustomerWindowComponent newCustomerWindowComponent;
    private final CustomerWindowComponent customerWindowComponent;
    private final NewProductWindowComponent newProductWindowComponent;
    private final ProductWindowComponent productWindowComponent;

    @Autowired
    public MainWindowController(
            TaxWindowComponent taxWindowComponent,
            NewTaxWindowComponent newTaxWindowComponent,
            MainWindowService mainWindowService,
            NewCustomerWindowComponent newCustomerWindowComponent,
            CustomerWindowComponent customerWindowComponent,
            NewProductWindowComponent newProductWindowComponent,
            ProductWindowComponent productWindowComponent) {
        this.taxWindowComponent = taxWindowComponent;
        this.newTaxWindowComponent = newTaxWindowComponent;
        this.mainWindowService = mainWindowService;
        this.newCustomerWindowComponent = newCustomerWindowComponent;
        this.customerWindowComponent = customerWindowComponent;
        this.newProductWindowComponent = newProductWindowComponent;
        this.productWindowComponent = productWindowComponent;
    }

    @FXML
    private Button taxButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button myCompanyButton;

    @FXML
    private Button customersButton;

    @FXML
    private Button invoicesButton;

    @FXML
    private Button productsButton;

    @FXML
    private Menu helpMenuButton;

    @FXML
    private Menu databaseMenuButton;

    @FXML
    private Menu fileMenuButton;

    @FXML
    private Menu addMenuButton;

    @FXML
    private VBox rightVBoxView;


    @FXML
    void onNewTaxMenuItemAction() {
        mainWindowService.onLoadComponent(
                newTaxWindowComponent,
                400,
                500,
                false,
                "Nowy podatek");
    }

    @FXML
    void onNewCustomerAction() {
        mainWindowService.onLoadComponent(
                newCustomerWindowComponent,
                400,
                500,
                false,
                "Nowy klient");
    }

    @FXML
    void onNewProductAction() {
        mainWindowService.onLoadComponent(
                newProductWindowComponent,
                400,
                500,
                false,
                "Nowy produkt");
    }

    @FXML
    void onCustomersButtonAction() {
        mainWindowService.onSubViewChange(customersButton, rightVBoxView, customerWindowComponent);
        changeButtonsStyle(customersButton);
    }

    @FXML
    void onInvoiceButtonAction() {

    }

    @FXML
    void onProductButtonAction() {
        mainWindowService.onSubViewChange(productsButton, rightVBoxView, productWindowComponent);
        changeButtonsStyle(productsButton);
    }

    @FXML
    void onTaxButtonClickAction() {
        mainWindowService.onSubViewChange(taxButton, rightVBoxView, taxWindowComponent);
        changeButtonsStyle(taxButton);
    }

    public void refreshTaxTableView() {
        mainWindowService.refreshSubView(taxWindowComponent, rightVBoxView);
    }

    public void refreshCustomerTableView() {
        mainWindowService.refreshSubView(customerWindowComponent, rightVBoxView);
    }

    public void refreshProductTableView() { mainWindowService.refreshSubView(productWindowComponent, rightVBoxView);}

    @Override
    public void initialize() {
        dashboardButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.DASHBOARD, "20px"));
        dashboardButton.setAlignment(Pos.BASELINE_LEFT);
        dashboardButton.setPadding(new Insets(0, 0, 0, 40));

        myCompanyButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.BUILDING, "20px"));
        myCompanyButton.setAlignment(Pos.BASELINE_LEFT);
        myCompanyButton.setPadding(new Insets(0, 0, 0, 40));

        customersButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.USERS, "20px"));
        customersButton.setAlignment(Pos.BASELINE_LEFT);
        customersButton.setPadding(new Insets(0, 0, 0, 40));

        invoicesButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FILE_PDF_ALT, "20px"));
        invoicesButton.setAlignment(Pos.BASELINE_LEFT);
        invoicesButton.setPadding(new Insets(0, 0, 0, 40));

        productsButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PRODUCT_HUNT, "20px"));
        productsButton.setAlignment(Pos.BASELINE_LEFT);
        productsButton.setPadding(new Insets(0, 0, 0, 40));

        taxButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MONEY, "20px"));
        taxButton.setAlignment(Pos.BASELINE_LEFT);
        taxButton.setPadding(new Insets(0, 0, 0, 40));

        helpMenuButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LIFE_BOUY, "15px"));
        databaseMenuButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.DATABASE, "15px"));
        fileMenuButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FILE, "15px"));
        addMenuButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS_CIRCLE, "15px"));
    }

    private void changeButtonsStyle(Button _button) {
        Button[] selectedButtons = new Button[] {
                dashboardButton,
                myCompanyButton,
                customersButton,
                invoicesButton,
                productsButton,
                taxButton};
        mainWindowService.findButtonForStyleChange(selectedButtons, _button);
    }
}
