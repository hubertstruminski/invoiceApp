package com.hubertstruminski.invoice.app.view;

import com.hubertstruminski.invoice.app.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewCreator {

    public void showLoginWindow() {
        BaseController controller = new LoginWindowController(this, "/static/loginWindow.fxml");
        initStage(controller, 550, 526);
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(this, "/static/mainWindow.fxml");
        initStage(controller, 850, 600);
    }

    public void showNewTaxWindow() {
        BaseController controller = new NewTaxWindowController(this, "/static/newTaxWindow.fxml");
        initStage(controller, 400, 500);
    }

    private void initStage(BaseController controller, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            return;
        }
        Scene scene = new Scene(parent, width, height);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
