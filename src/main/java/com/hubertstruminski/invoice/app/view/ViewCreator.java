package com.hubertstruminski.invoice.app.view;

import com.hubertstruminski.invoice.app.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
//@Scope("singleton")
public class ViewCreator {

//    public void showLoginWindow() {
//        BaseController controller = new LoginWindowController(this, "/static/loginWindow.fxml");
//        initStage(controller, 550, 526);
//    }

    public void showNewTaxWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/static/newTaxWindow.fxml"));
//        BaseController controller = new NewTaxWindowController(this, "/static/newTaxWindow.fxml");
//        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            return;
        }
        Scene scene = new Scene(parent, 400, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    private Stage initStage(BaseController controller, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
        Scene scene = new Scene(parent, width, height);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}
