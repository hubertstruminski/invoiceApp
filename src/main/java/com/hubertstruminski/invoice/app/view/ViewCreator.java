package com.hubertstruminski.invoice.app.view;

import com.hubertstruminski.invoice.app.controller.BaseController;
import com.hubertstruminski.invoice.app.controller.LoginWindowController;
import com.hubertstruminski.invoice.app.controller.MainWindowController;
import com.hubertstruminski.invoice.app.controller.MenuWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewCreator {

    public void showMenuWindow() {
        BaseController controller = new MenuWindowController(this, "/static/menuWindow.fxml");
        initStage(controller, 732, 526);
    }

    public void showConfigurationWindow() {

    }

    public void showLoginWindow() {
        BaseController controller = new LoginWindowController(this, "/static/loginWindow.fxml");
        initStage(controller, 550, 526);
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(this, "/static/mainWindow.fxml");
        initStage(controller, 850, 600);
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
