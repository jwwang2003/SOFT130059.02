package com.example.lab10.controller;

import com.example.lab10.Main;
import com.example.lab10.gamelogic.PlayerSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PlayerData extends ListCell<PlayerSession> {
    @FXML
    VBox playerContainer;

    @FXML
    Label playerName;

    @FXML
    Label playerScore;

    @FXML
    Label playerHasWon;

    public PlayerData() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("PlayerData.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(PlayerSession item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            this.playerContainer.setStyle(
                    "-fx-background-color: red"
            );
            this.playerName.setText(item.getUsername());
            this.playerName.setTextFill(item.getPlayerColor());

            if(item.checkGameEnd()) this.playerHasWon.setText("Win");

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}