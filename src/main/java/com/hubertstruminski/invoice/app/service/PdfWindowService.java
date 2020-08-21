package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.model.Company;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.CompanyRepository;
import com.itextpdf.html2pdf.HtmlConverter;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;
import java.util.List;

@Service
public class PdfWindowService {

    private final CompanyRepository companyRepository;

    @Autowired
    public PdfWindowService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void setColumnConstraints(GridPane gridPane) {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100.0);
        columnConstraints.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().add(columnConstraints);
    }

    public Company findCompany() {
        Iterable<Company> all = companyRepository.findAll();
        Iterator<Company> iterator = all.iterator();
        if(iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public void addButtonRowConstraint(GridPane gridPane) {
        RowConstraints buttonRow = new RowConstraints();
        buttonRow.setPercentHeight(4);
        buttonRow.setValignment(VPos.CENTER);
        gridPane.getRowConstraints().add(buttonRow);
    }

    public void addWebViewRowConstraint(GridPane gridPane) {
        RowConstraints webViewRow = new RowConstraints();
        webViewRow.setPercentHeight(96);
        gridPane.getRowConstraints().add(webViewRow);
    }

    public Button addDownloadPdfButtonToGridPane(GridPane gridPane, WebView webView) {
        final Button downloadPdfButton = new Button("Pobierz PDF");
        gridPane.add(downloadPdfButton, 0, 0);
        gridPane.add(webView, 0, 1);

        return downloadPdfButton;
    }

    public void showStage(GridPane gridPane) {
        Stage stage = new Stage();
        Scene scene = new Scene(gridPane, 950, 842);
        stage.setScene(scene);
        stage.setTitle("Faktura - PDF");
        stage.show();
    }

    public File createFile() {
        File file = null;
        try {
            file = new File("filename.html");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void processFileStream(File file, String html) {
        OutputStream outputStream;
        try {
            File absoluteFile = file.getAbsoluteFile();
            outputStream = new FileOutputStream(absoluteFile);
            Writer writer=new OutputStreamWriter(outputStream);
            writer.write(html);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generatePdf(File file, String fileName) {
        try {
            HtmlConverter.convertToPdf(new FileInputStream(file),
                    new FileOutputStream("./" + fileName + ".pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderProductsDataToInvoicePdf(List<Product> products, JSObject jsObject) {
        for(Product x: products) {
            Tax tax = x.getTax();
            jsObject.setMember("product", x);
            jsObject.setMember("_tax", tax);
            jsObject.call("renderSingleProduct");
        }
    }
}
