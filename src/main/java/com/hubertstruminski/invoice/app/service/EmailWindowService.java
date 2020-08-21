package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.component.EmailFailureSendWindowComponent;
import com.hubertstruminski.invoice.app.component.EmailSuccessfullySendWindowComponent;
import com.hubertstruminski.invoice.app.controller.MainWindowController;
import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Status;
import com.hubertstruminski.invoice.app.repository.InvoiceRepository;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailWindowService {

    private final InvoiceRepository invoiceRepository;
    private final MainWindowService mainWindowService;
    private final MainWindowController mainWindowController;
    private final EmailSuccessfullySendWindowComponent emailSuccessfullySendWindowComponent;
    private final EmailFailureSendWindowComponent emailFailureSendWindowComponent;

    @Autowired
    public EmailWindowService(
            InvoiceRepository invoiceRepository,
            MainWindowService mainWindowService,
            MainWindowController mainWindowController,
            EmailSuccessfullySendWindowComponent emailSuccessfullySendWindowComponent,
            EmailFailureSendWindowComponent emailFailureSendWindowComponent) {
        this.invoiceRepository = invoiceRepository;
        this.mainWindowController = mainWindowController;
        this.mainWindowService = mainWindowService;
        this.emailSuccessfullySendWindowComponent = emailSuccessfullySendWindowComponent;
        this.emailFailureSendWindowComponent = emailFailureSendWindowComponent;
    }

    public void handleError(boolean isError, Invoice invoice, VBox vBox) {
        if(!isError) {
            invoice.setStatus(Status.SENT);
            invoiceRepository.save(invoice);

            mainWindowController.refreshCustomerTableView();
            mainWindowService.onLoadComponent(
                    emailSuccessfullySendWindowComponent,
                    550,
                    200,
                    false,
                    "Sukces");
            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
        } else {
            mainWindowService.onLoadComponent(
                    emailFailureSendWindowComponent,
                    550,
                    200,
                    false,
                    "Błąd");
        }
    }
}
