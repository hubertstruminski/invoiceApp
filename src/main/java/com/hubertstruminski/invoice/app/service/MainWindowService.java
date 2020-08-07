package com.hubertstruminski.invoice.app.service;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlComponent;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.model.fxml.FxmlLoadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainWindowService {

    private final EasyFxml easyFxml;

    @Autowired
    public MainWindowService(EasyFxml easyFxml) {
        this.easyFxml = easyFxml;
    }

    public void onLoadComponent(FxmlComponent fxmlComponent, int width, int height, boolean isResizable, String title) {
        FxmlLoadResult<Pane, FxmlController> load = easyFxml.load(fxmlComponent);

        load.afterNodeLoaded(pane -> {
            Scene scene = new Scene(pane, width, height);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(isResizable);
            stage.setTitle(title);

            stage.show();
        });
    }

    public void onSubViewChange(Button customersButton, VBox rightVBoxView, FxmlComponent fxmlComponent) {
        customersButton.setOnMouseClicked(mouseEvent ->
                easyFxml.load(fxmlComponent).afterNodeLoaded(pane -> {
                    VBox vBox = (VBox) pane;
                    vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                    rightVBoxView.getChildren().setAll(vBox);
                }));
    }

    public void refreshSubView(FxmlComponent fxmlComponent, VBox rightVBoxView) {
        easyFxml.load(fxmlComponent)
                .afterNodeLoaded(pane -> {
                    VBox vBox = (VBox) pane;
                    vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                    rightVBoxView.getChildren().setAll(vBox);
                });
    }

    public void findButtonForStyleChange(Button[] selectedButtons, Button _button) {
        for(Button button: selectedButtons) {
            if(button.equals(_button)) {
                button.getStyleClass().clear();
                button.getStyleClass().add("leftMenuButtonsActive");
            } else {
                button.getStyleClass().clear();
                button.getStyleClass().add("leftMenuButtons");
            }
        }
    }
}
