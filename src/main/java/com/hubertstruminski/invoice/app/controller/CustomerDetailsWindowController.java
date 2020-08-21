package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.EmailWindowComponent;
import com.hubertstruminski.invoice.app.component.LoginWindowComponent;
import com.hubertstruminski.invoice.app.component.PdfWindowComponent;
import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.model.Status;
import com.hubertstruminski.invoice.app.repository.InvoiceRepository;
import com.hubertstruminski.invoice.app.service.MainWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerDetailsWindowController implements FxmlController {

    private final InvoiceRepository invoiceRepository;
    private final MainWindowService mainWindowService;
    private final LoginWindowComponent loginWindowComponent;
    private final EasyFxml easyFxml;
    private final PdfWindowComponent pdfWindowComponent;
    private final PdfWindowController pdfWindowController;
    private final EmailWindowComponent emailWindowComponent;
    private final EmailWindowController emailWindowController;

    @Autowired
    public CustomerDetailsWindowController(
            InvoiceRepository invoiceRepository,
            MainWindowService mainWindowService,
            EasyFxml easyFxml,
            PdfWindowComponent pdfWindowComponent,
            PdfWindowController pdfWindowController,
            LoginWindowComponent loginWindowComponent,
            EmailWindowComponent emailWindowComponent,
            EmailWindowController emailWindowController) {
        this.invoiceRepository = invoiceRepository;
        this.mainWindowService = mainWindowService;
        this.loginWindowComponent = loginWindowComponent;
        this.easyFxml = easyFxml;
        this.pdfWindowComponent = pdfWindowComponent;
        this.pdfWindowController = pdfWindowController;
        this.emailWindowComponent = emailWindowComponent;
        this.emailWindowController = emailWindowController;
    }

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailValueLabel;

    @FXML
    private Button emailSendButton;

    @FXML
    private Label phoneNumberValueLabel;

    @FXML
    private Label nipValueLabel;

    @FXML
    private Label addressValueLabel;

    @FXML
    private Label countryValueLabel;

    @FXML
    private Label websiteValueLabel;

    @FXML
    private Label commentValueLabel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void onEmailSendButtonAction() {

    }

    @Override
    public void initialize() {
    }

    public void setTextFields(Customer customer) {
        nameLabel.setText(customer.getName());
        emailValueLabel.setText(customer.getEmail());
        phoneNumberValueLabel.setText(customer.getPhoneNumber());
        nipValueLabel.setText(customer.getNip());
        addressValueLabel.setText(customer.getAddress().getAddress());
        countryValueLabel.setText(customer.getAddress().getCountry());
        websiteValueLabel.setText(customer.getWebsite());
        commentValueLabel.setText(customer.getNote());

        List<Invoice> invoices = invoiceRepository.findAllByCustomer(customer);

        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        for(Invoice invoice: invoices) {
            GridPane gridPane = new GridPane();

            setColumnConstraints(gridPane, 20.0, HPos.LEFT);
            setColumnConstraints(gridPane, 35.0, HPos.LEFT);
            //...
            setColumnConstraints(gridPane, 13.75, HPos.LEFT);
            setColumnConstraints(gridPane, 13.75, HPos.LEFT);
            setColumnConstraints(gridPane, 12.5, HPos.LEFT);

            gridPane.add(new Label("Faktura"), 0, 0);
            gridPane.add(new Label("Numer: " + invoice.getNumber()), 0, 1); // i - column, i1 - row
            gridPane.add(new Label("Data: " + invoice.getDate()), 0 ,2);
            gridPane.add(new Label("Termin: " + invoice.getDeadline()), 0,3);

            gridPane.add(new Label("Klient"), 1, 0);
            gridPane.add(new Label("Nazwa: " + invoice.getCustomer().getName()), 1, 1); // i - column, i1 - row
            gridPane.add(new Label("Adres: " + invoice.getCustomer().getAddress().getAddress()
                    + " " + invoice.getCustomer().getAddress().getCountry()), 1 ,2);
            gridPane.add(new Label("Telefon: " + invoice.getCustomer().getPhoneNumber()), 1,3);

            Button sendButton = new Button("Wyślij");
            sendButton.setStyle(Constants.WARNING_BUTTON_STYLES);
            gridPane.add(new Label("Wyślij email"), 2, 0);
            gridPane.add(sendButton, 2, 1, 2, 3); // i - column, i1 - row

            sendButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//                easyFxml.load(loginWindowComponent); // DISABLED - GOOGLE LOGIN WITH STORE TOKENS IN DATABASE
                // ALLOW ONLY FOR GMAIL AUTHORIZATION
                mainWindowService.onLoadComponent(
                        emailWindowComponent,
                        700,
                        600,
                        true,
                        "Wyślij e-mail");
                emailWindowController.setInvoice(invoice);
            });

            Button pdfButton = new Button("PDF");

            pdfButton.setStyle(Constants.DANGER_BUTTON_STYLES);
            gridPane.add(new Label("Generuj PDF"), 3, 0);
            gridPane.add(pdfButton, 3, 1, 3, 3); // i - column, i1 - row

            pdfButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                easyFxml.load(pdfWindowComponent);
                pdfWindowController.setInvoice(invoice);
            });

            Label status = new Label();

            if(invoice.getStatus() == Status.NOT_SENT) {
                status.setStyle(Constants.RED_COLOR_FONT);
            } else {
                status.setStyle(Constants.GREEN_COLOR_FONT);
            }
            status.setText(invoice.getStatus().getStatus());
            gridPane.add(new Label("Status"), 4, 0);
            gridPane.add(status, 4, 1, 4, 3); // i - column, i1 - row

            VBox vBox = new VBox(gridPane);
            vBox.setFillWidth(true);

            vBox.setStyle(Constants.INVOICE_DETAILS_PRODUCTS_ITEMS);
            mainVBox.getChildren().add(vBox);
        }
    }

    private void setColumnConstraints(GridPane gridPane, double width, HPos hPos) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(width);
        columnConstraints.setHalignment(hPos);
        gridPane.getColumnConstraints().add(columnConstraints);
    }
}
