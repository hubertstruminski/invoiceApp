package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.NewCustomerWindowComponent;
import com.hubertstruminski.invoice.app.component.NewTaxWindowComponent;
import com.hubertstruminski.invoice.app.component.TaxWindowComponent;
import com.hubertstruminski.invoice.app.service.MainService;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.model.fxml.FxmlLoadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.function.Consumer;

@Controller
public class MainWindowController implements FxmlController {

    private TaxWindowComponent taxWindowComponent;
    private NewTaxWindowComponent newTaxWindowComponent;
    private EasyFxml easyFxml;
    private MainService mainService;
    private NewCustomerWindowComponent newCustomerWindowComponent;

    @Autowired
    public MainWindowController(
            TaxWindowComponent taxWindowComponent,
            NewTaxWindowComponent newTaxWindowComponent,
            EasyFxml easyFxml,
            MainService mainService,
            NewCustomerWindowComponent newCustomerWindowComponent) {
        this.taxWindowComponent = taxWindowComponent;
        this.newTaxWindowComponent = newTaxWindowComponent;
        this.easyFxml = easyFxml;
        this.mainService = mainService;
        this.newCustomerWindowComponent = newCustomerWindowComponent;
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
    void onNewTaxMenuItemAction(ActionEvent event) {
        mainService.onLoadComponent(
                newTaxWindowComponent,
                400,
                500,
                false,
                "Nowy podatek");
    }

    @FXML
    void onNewCustomerAction(ActionEvent event) {
        mainService.onLoadComponent(
                newCustomerWindowComponent,
                400,
                500,
                false,
                "Nowy klient");
    }


    @FXML
    void onTaxButtonClickAction(ActionEvent event) {
        taxButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FxmlLoadResult<Pane, FxmlController> load = easyFxml.load(taxWindowComponent);
                load.afterNodeLoaded(new Consumer<Pane>() {
                    @Override
                    public void accept(Pane pane) {
                        VBox vBox = (VBox) pane;
                        vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                        rightVBoxView.getChildren().setAll(vBox);
                    }
                });
            }
        });
    }

    public void refreshTaxTableView() {
        easyFxml.load(taxWindowComponent)
        .afterNodeLoaded(new Consumer<Pane>() {
            @Override
            public void accept(Pane pane) {
                VBox vBox = (VBox) pane;
                vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                rightVBoxView.getChildren().setAll(vBox);
            }
        });
    }


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
}
