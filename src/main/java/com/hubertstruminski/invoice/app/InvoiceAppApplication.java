package com.hubertstruminski.invoice.app;

import javafx.stage.Stage;
import moe.tristan.easyfxml.FxApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class InvoiceAppApplication extends FxApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected SpringApplicationBuilder getSab() {
        return new SpringApplicationBuilder(getClass())
                .main(getClass())
                .headless(false)
                .web(WebApplicationType.SERVLET);
    }

    @Override
    public void start(Stage primaryStage) {
        super.start(primaryStage);
        primaryStage.setTitle("System do zarządzania fakturami");
    }
}
