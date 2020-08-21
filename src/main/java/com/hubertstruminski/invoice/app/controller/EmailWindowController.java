package com.hubertstruminski.invoice.app.controller;

import com.google.api.services.gmail.Gmail;
import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.service.EmailWindowService;
import com.hubertstruminski.invoice.app.service.GmailService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.MessagingException;

@Controller
public class EmailWindowController implements FxmlController {

    private final GmailService gmailService;
    private final EmailWindowService emailWindowService;

    private Invoice invoice = null;
    private VBox mainVBox = null;
    private List<File> files = null;

    private boolean isError;
    private boolean isEmailRecipientEmpty;
    private boolean isEmailSenderError;

    @Autowired
    public EmailWindowController(
            GmailService gmailService,
            EmailWindowService emailWindowService) {
        this.gmailService = gmailService;
        this.emailWindowService = emailWindowService;
    }

    @FXML
    private VBox vBox;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField emailRecipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private Label emailRecipientErrorLabel;

    @FXML
    private TextField emailSenderTextField;

    @FXML
    private Label emailSenderErrorLabel;


    @FXML
    void onSendEmailButtonAction() {
        if(emailRecipientTextField.getText().length() == 0) {
            isEmailRecipientEmpty = true;
        } else {
            isEmailRecipientEmpty = false;
        }

        if(emailSenderTextField.getText().length() == 0) {
            isEmailSenderError = true;
        } else {
            isEmailSenderError = false;
        }

        if(isEmailRecipientEmpty) {
            emailRecipientErrorLabel.setText("Pole odbiorca jest wymagane.");
        } else {
            emailRecipientErrorLabel.setText("");
        }

        if(isEmailSenderError) {
            emailSenderErrorLabel.setText("Wpisz e-mail, którego użyłeś do logowania.");
        } else {
            emailSenderErrorLabel.setText("");
        }

        if(!isEmailRecipientEmpty && !isEmailSenderError) {
            try {
                Gmail service = gmailService.startGmailService();
                gmailService.sendMessage(service, htmlEditor.getHtmlText(), files, emailRecipientTextField.getText(),
                        emailSenderTextField.getText(), subjectTextField.getText());
            } catch (IOException | GeneralSecurityException | MessagingException e) {
                isError = true;
                e.printStackTrace();
            }
            emailWindowService.handleError(isError, invoice, vBox);
        }
    }

    @FXML
    void onUploadPdfButtonAction() {
        final FileChooser fileChooser = new FileChooser();

        Stage stage = (Stage) vBox.getScene().getWindow();
        files = fileChooser.showOpenMultipleDialog(stage);
        if (files != null) {
            for (File file : files) {
                Label label = new Label(file.getName());
                mainVBox.getChildren().add(label);
            }
        }
    }

    @Override
    public void initialize() {
        invoice = new Invoice();
        mainVBox = new VBox();
        files = new ArrayList<>();

        isError = false;
        isEmailRecipientEmpty = false;
        isEmailSenderError = false;

        emailRecipientErrorLabel.setText("");
        emailRecipientErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        emailSenderErrorLabel.setText("");
        emailSenderErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);
    }

    public void setInvoice(Invoice _invoice) {
        invoice = _invoice;
    }
}
