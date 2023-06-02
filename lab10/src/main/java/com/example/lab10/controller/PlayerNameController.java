package com.example.lab10.controller;

import com.example.lab10.model.GameHolder;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PlayerNameController {
    @FXML
    TextField playerInput1, playerInput2;

    @FXML
    Button confirm;

    @FXML
    private void initialize() {
        GameHolder gameHolder = GameHolder.getInstance();
        if(gameHolder.playerSessionList.size() <= 1) {
            playerInput2.setDisable(true);
        } else {

        }
    }
}
