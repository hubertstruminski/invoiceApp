package com.hubertstruminski.invoice.app.service;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlComponent;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.model.fxml.FxmlLoadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class MainService {

    private EasyFxml easyFxml;

    @Autowired
    public MainService(EasyFxml easyFxml) {
        this.easyFxml = easyFxml;
    }

    public void onLoadComponent(FxmlComponent fxmlComponent, int width, int height, boolean isResizable, String title) {
        FxmlLoadResult<Pane, FxmlController> load = easyFxml.load(fxmlComponent);

        load.afterNodeLoaded(new Consumer<Pane>() {
            @Override
            public void accept(Pane pane) {
                Scene scene = new Scene(pane, width, height);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(isResizable);
                stage.setTitle(title);

                stage.show();
            }
        });
    }
}
