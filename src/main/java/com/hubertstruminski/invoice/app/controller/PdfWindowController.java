package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.*;
import com.hubertstruminski.invoice.app.repository.CompanyRepository;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.itextpdf.html2pdf.HtmlConverter;

import netscape.javascript.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class PdfWindowController implements FxmlController {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    private Tax tax = null;
    private Product product;

    @Autowired
    public PdfWindowController(
            ProductRepository productRepository,
            CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    private Invoice invoice = null;
    private Customer customer = null;
    private Company company = null;
    private String html = null;

    @FXML
    private WebView webView;

    @FXML
    private Button downloadPdfButton;

    @Override
    public void initialize() {
        webView = new WebView();
        WebEngine engine = webView.getEngine();
        String url = getClass().getResource("/static/pdf.html").toExternalForm();
        engine.load(url);
        engine.setJavaScriptEnabled(true);

        GridPane gridPane = new GridPane();

        setColumnConstraints(gridPane, 100.00, HPos.CENTER);

        RowConstraints buttonRow = new RowConstraints();
        buttonRow.setPercentHeight(4);
        buttonRow.setValignment(VPos.CENTER);
        gridPane.getRowConstraints().add(buttonRow);

        RowConstraints webViewRow = new RowConstraints();
        webViewRow.setPercentHeight(96);
        gridPane.getRowConstraints().add(webViewRow);

        final Button downloadPdfButton = new Button("Pobierz PDF");
        gridPane.add(downloadPdfButton, 0, 0);
        gridPane.add(webView, 0, 1);

//        Scale scale = new Scale(scaleFactor, scaleFactor);
//        scale.setPivotX(0);
//        scale.setPivotY(0);
//        scene.getRoot().getTransforms().setAll(scale);

        Stage stage = new Stage();
        Scene scene = new Scene(gridPane, 950, 842);
        stage.setScene(scene);
        stage.setTitle("Faktura - PDF");
        stage.show();

        downloadPdfButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            File file = null;
            try {
                file = new File("filename.html");
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(html);
            OutputStream outputStream = null;
            try {
                File absoluteFile = file.getAbsoluteFile();
                outputStream = new FileOutputStream(absoluteFile);
                Writer writer=new OutputStreamWriter(outputStream);
                writer.write(html);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                HtmlConverter.convertToPdf(new FileInputStream(file),
                        new FileOutputStream("./index-to-pdf.pdf"));
            } catch (IOException e) {
                e.printStackTrace();
            }
//            boolean success = HtmlToPdf.create()
//                    .object(HtmlToPdfObject.forHtml(html))
//                    .convert("./htmltopdf.pdf");
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

                        company = findCompany();
                        jsObject.setMember("company", company);
                        jsObject.call("renderCompany");

                        for(Product x: products) {
                            product = x;
                            tax = x.getTax();
                            jsObject.setMember("product", product);
                            jsObject.setMember("_tax", tax);
                            jsObject.call("renderSingleProduct");
                        }

                        jsObject.call("renderSum");
                        jsObject.call("takeDataForGeneratePdf");

                        String htmlData = (String) engine.executeScript("html;");
                        html = htmlData;
                    }
                });
    }

    public void setInvoice(Invoice _invoice) {
        invoice = _invoice;
    }

    private void setColumnConstraints(GridPane gridPane, double width, HPos hPos) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(width);
        columnConstraints.setHalignment(hPos);
        gridPane.getColumnConstraints().add(columnConstraints);
    }

    private Company findCompany() {
        Iterable<Company> all = companyRepository.findAll();
        Iterator<Company> iterator = all.iterator();
        if(iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
