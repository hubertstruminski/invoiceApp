package com.hubertstruminski.invoice.app;

import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class InvoiceAppApplication {

//    private ConfigurableApplicationContext springContext;
//    private FXMLLoader fxmlLoader;
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        ViewCreator viewCreator = new ViewCreator();
//        viewCreator.showMenuWindow();
//
//
//    }

    public static void main(String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }

//    @Override
//    public void init() throws Exception {
//        springContext = SpringApplication.run(InvoiceAppApplication.class);
//        fxmlLoader = new FXMLLoader();
//        fxmlLoader.setControllerFactory(springContext::getBean);
//    }
//
//    @Override
//    public void stop() {
//        springContext.stop();
//    }
}
