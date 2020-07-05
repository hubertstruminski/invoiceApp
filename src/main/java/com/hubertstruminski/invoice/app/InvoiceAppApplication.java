package com.hubertstruminski.invoice.app;

import com.hubertstruminski.invoice.app.repository.TaxRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.*;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hubertstruminski.invoice.app.repository",
        "com.hubertstruminski.invoice.app.controller",
        "com.hubertstruminski.invoice.app.model",
        "com.hubertstruminski.invoice.app.service",
        "com.hubertstruminski.invoice.app.view"})
public class InvoiceAppApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;

    public static void main(String[] args) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(InvoiceAppApplication.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/static/mainWindow.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        rootNode = fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(rootNode));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
