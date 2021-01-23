package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.*;
import com.hubertstruminski.invoice.app.service.PdfWindowService;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import netscape.javascript.*;

import java.io.*;
import java.util.List;

@Controller
public class PdfWindowController implements FxmlController {

    private final PdfWindowService pdfWindowService;

    @Autowired
    public PdfWindowController(PdfWindowService pdfWindowService) {
        this.pdfWindowService = pdfWindowService;
    }

    private Invoice invoice = null;
    private Customer customer = null;
    private Company company = null;
    private String html = null;

    @FXML
    private WebView webView;

    @Override
    public void initialize() {
        webView = new WebView();
        WebEngine engine = webView.getEngine();
        String url = getClass().getResource("/static/pdf.html").toExternalForm();
        engine.load(url);
        engine.setJavaScriptEnabled(true);

        GridPane gridPane = new GridPane();

        pdfWindowService.setColumnConstraints(gridPane);
        pdfWindowService.addButtonRowConstraint(gridPane);
        pdfWindowService.addWebViewRowConstraint(gridPane);

        Button downloadPdfButton = pdfWindowService.addDownloadPdfButtonToGridPane(gridPane, webView);
        pdfWindowService.showStage(gridPane);

        downloadPdfButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            File file = pdfWindowService.createFile();
            pdfWindowService.processFileStream(file, html);
            pdfWindowService.generatePdf(file, invoice.getNumber());
            file.delete();
        });

        engine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        List<Product> products = invoice.getProducts();
                        JSObject jsObject = (JSObject) engine.executeScript("window");

                        jsObject.setMember("invoice", invoice);
                        jsObject.call("renderInvoice");

                        customer = invoice.getCustomer();
                        jsObject.setMember("customer", customer);
                        jsObject.call("renderCustomer");

                        company = pdfWindowService.findCompany();
                        jsObject.setMember("company", company);
                        jsObject.call("renderCompany");

                        pdfWindowService.renderProductsDataToInvoicePdf(products, jsObject);

                        jsObject.call("renderSum");
                        jsObject.call("takeDataForGeneratePdf");

                        html = (String) engine.executeScript("html;");
                    }
                });
    }

    public void setInvoice(Invoice _invoice) {
        invoice = _invoice;
    }
}
