package com.hubertstruminski.invoice.app;

import com.hubertstruminski.invoice.app.controller.NewTaxWindowController;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

@SpringBootApplication
@EntityScan(basePackages = {"com.hubertstruminski.invoice.app.model"})
//@EnableJpaRepositories(basePackages = {"com.hubertstruminski.invoice.app.repository"}, entityManagerFactoryRef = "entityManagerFactory")
//@PropertySource("classpath:application.properties")
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
//        fxmlLoader.setController(new NewTaxWindowController());
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
