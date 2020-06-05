package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.JavaFXApplication;
import com.hubertstruminski.invoice.app.controller.MenuWindowController;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<JavaFXApplication.StageReadyEvent> {
    @Override
    public void onApplicationEvent(JavaFXApplication.StageReadyEvent event) {
        ViewCreator viewCreator = new ViewCreator();
        viewCreator.showMenuWindow();
    }
}
