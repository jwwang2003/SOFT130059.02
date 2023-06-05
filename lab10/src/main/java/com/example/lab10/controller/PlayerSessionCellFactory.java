package com.example.lab10.controller;

import com.example.lab10.gamelogic.PlayerSession;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


public class PlayerSessionCellFactory implements Callback<ListView<PlayerSession>, ListCell<PlayerSession>> {

    @Override
    public ListCell<PlayerSession> call(ListView<PlayerSession> param) {
        return new PlayerData();
    }
}