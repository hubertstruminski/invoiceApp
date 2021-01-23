package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.util.Constants;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Service;

@Service
public class ChooseCoreService {

    public VBox createVBox(GridPane gridPane) {
        VBox vBox = new VBox(gridPane);
        vBox.setFillWidth(true);

        vBox.setMaxWidth(400);
        vBox.setMinWidth(400);

        vBox.setStyle(Constants.CHOOSE_INTERFACE_ITEMS);
        return vBox;
    }
}
