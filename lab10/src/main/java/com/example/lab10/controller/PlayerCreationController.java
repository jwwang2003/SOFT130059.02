package com.example.lab10.controller;

import com.example.lab10.gamelogic.PlayerSession;
import com.example.lab10.model.GameHolder;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class PlayerCreationController {
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

    public void init(Runnable callback1) {
        confirm.setOnAction(event -> {
            GameHolder gameHolder = GameHolder.getInstance();

            if(gameHolder.playerSessionList.size() > 1) {
                gameHolder.addPlayerSession(new PlayerSession(playerInput1.getText(), Color.RED));
            }

            gameHolder.addPlayerSession(new PlayerSession(playerInput1.getText(), Color.GREEN));

            callback1.run();
        });
    }
}
