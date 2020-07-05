package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.view.ViewCreator;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Scope("prototype")
public class MainWindowController extends BaseController implements Initializable {

    @Autowired
    private ViewCreator viewCreator;

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


    public MainWindowController() {

    }

    public MainWindowController(ViewCreator viewCreator, String fxmlName) {
        super(viewCreator, fxmlName);
    }

    @FXML
    void onNewTaxMenuItemAction(ActionEvent event) {
//        ViewCreator creator = new ViewCreator();
//        creator.showNewTaxWindow();
        viewCreator.showNewTaxWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
