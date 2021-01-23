package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.component.EmailWindowComponent;
import com.hubertstruminski.invoice.app.component.PdfWindowComponent;
import com.hubertstruminski.invoice.app.controller.EmailWindowController;
import com.hubertstruminski.invoice.app.controller.PdfWindowController;
import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Status;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.EasyFxml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsWindowService {

    private final MainWindowService mainWindowService;
    private final EmailWindowComponent emailWindowComponent;
    private final EmailWindowController emailWindowController;
    private final EasyFxml easyFxml;
    private final PdfWindowComponent pdfWindowComponent;
    private final PdfWindowController pdfWindowController;

    @Autowired
    public CustomerDetailsWindowService(
            MainWindowService mainWindowService,
            EmailWindowComponent emailWindowComponent,
            EmailWindowController emailWindowController,
            EasyFxml easyFxml,
            PdfWindowComponent pdfWindowComponent,
            PdfWindowController pdfWindowController) {
        this.mainWindowService = mainWindowService;
        this.emailWindowComponent = emailWindowComponent;
        this.emailWindowController = emailWindowController;
        this.easyFxml = easyFxml;
        this.pdfWindowComponent = pdfWindowComponent;
        this.pdfWindowController = pdfWindowController;
    }

    private void setColumnConstraints(GridPane gridPane, double width) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(width);
        columnConstraints.setHalignment(HPos.LEFT);
        gridPane.getColumnConstraints().add(columnConstraints);
    }

    public VBox createVBox(GridPane gridPane) {
        VBox vBox = new VBox(gridPane);
        vBox.setFillWidth(true);

        vBox.setStyle(Constants.INVOICE_DETAILS_PRODUCTS_ITEMS);

        return vBox;
    }

    public void setListColumnConstraints(GridPane gridPane) {
        setColumnConstraints(gridPane, 20.0);
        setColumnConstraints(gridPane, 35.0);
        setColumnConstraints(gridPane, 13.75);
        setColumnConstraints(gridPane, 13.75);
        setColumnConstraints(gridPane, 12.5);
    }

    public void setGridPaneLayout(GridPane gridPane, Invoice invoice) {
        gridPane.add(new Label("Faktura"), 0, 0);
        gridPane.add(new Label("Numer: " + invoice.getNumber()), 0, 1); // i - column, i1 - row
        gridPane.add(new Label("Data: " + invoice.getDate()), 0 ,2);
        gridPane.add(new Label("Termin: " + invoice.getDeadline()), 0,3);

        gridPane.add(new Label("Klient"), 1, 0);
        gridPane.add(new Label("Nazwa: " + invoice.getCustomer().getName()), 1, 1); // i - column, i1 - row
        gridPane.add(new Label("Adres: " + invoice.getCustomer().getAddress().getAddress()
                + " " + invoice.getCustomer().getAddress().getCountry()), 1 ,2);
        gridPane.add(new Label("Telefon: " + invoice.getCustomer().getPhoneNumber()), 1,3);
    }

    public Button setGridPaneSendButtonLayout(GridPane gridPane) {
        Button sendButton = new Button("Wyślij");
        sendButton.setStyle(Constants.WARNING_BUTTON_STYLES);
        gridPane.add(new Label("Wyślij email"), 2, 0);
        gridPane.add(sendButton, 2, 1, 2, 3); // i - column, i1 - row
        return sendButton;
    }

    public void addEventHandlerForSendButton(Button sendButton, Invoice invoice) {
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
    }

    public void setGridPanePdfButtonLayout(GridPane gridPane, Invoice invoice) {
        Button pdfButton = new Button("PDF");

        pdfButton.setStyle(Constants.DANGER_BUTTON_STYLES);
        gridPane.add(new Label("Generuj PDF"), 3, 0);
        gridPane.add(pdfButton, 3, 1, 3, 3); // i - column, i1 - row

        pdfButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            easyFxml.load(pdfWindowComponent);
            pdfWindowController.setInvoice(invoice);
        });
    }

    public void setGridPaneStatusLayout(GridPane gridPane, Invoice invoice) {
        Label status = new Label();

        if(invoice.getStatus() == Status.NOT_SENT) {
            status.setStyle(Constants.RED_COLOR_FONT);
        } else {
            status.setStyle(Constants.GREEN_COLOR_FONT);
        }
        status.setText(invoice.getStatus().getStatus());
        gridPane.add(new Label("Status"), 4, 0);
        gridPane.add(status, 4, 1, 4, 3); // i - column, i1 - row
    }
}
