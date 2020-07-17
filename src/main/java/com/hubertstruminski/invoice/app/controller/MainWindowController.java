package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.NewTaxWindowComponent;
import com.hubertstruminski.invoice.app.fx.manager.MainWindowUiManager;
import com.hubertstruminski.invoice.app.fx.manager.NewTaxWindowUiManager;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.vavr.control.Try;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.FxUiManager;
import moe.tristan.easyfxml.api.FxmlComponent;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.model.fxml.FxmlLoadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.function.Consumer;

@Controller
public class MainWindowController implements FxmlController {

    @Autowired
    private ViewCreator viewCreator;

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private NewTaxWindowComponent newTaxWindowComponent;

    @Autowired
    private EasyFxml easyFxml;

    @Autowired
    private MainWindowUiManager mainWindowUiManager;

    @Autowired
    private NewTaxWindowUiManager newTaxWindowUiManager;

    @FXML
    private VBox leftMenuVBox;

    @FXML
    private GridPane leftMenuGridPane;

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
    void onNewTaxMenuItemAction(ActionEvent event) {
        FxmlLoadResult<Pane, FxmlController> load = easyFxml.load(newTaxWindowComponent);



        load.afterNodeLoaded(new Consumer<Pane>() {
            @Override
            public void accept(Pane pane) {
                Scene scene = new Scene(pane, 400, 500);
                Stage stage = new Stage();
                stage.setScene(scene);

                stage.show();
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
