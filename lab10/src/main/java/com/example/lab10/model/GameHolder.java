package com.example.lab10.model;

import com.example.lab10.gamelogic.PlayerSession;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.entities.GameBoy;

import java.util.ArrayList;
import java.util.List;

public class GameHolder {
    public List<PlayerSession> playerSessionList = new ArrayList<>();
    private boolean playState = false;

    private final static GameHolder INSTANCE = new GameHolder();

    private GameHolder() {}

    public static GameHolder getInstance() {
        return INSTANCE;
    }

    public void setPlayState(boolean bool) { this.playState = bool; }

    public boolean getPlayState() {
        return this.playState;
    }

    public boolean hasPlayer(Position pos) {
        for(PlayerSession pS: playerSessionList) {
            return pS.getGameMap().getElementAt(pos) instanceof GameBoy;
        }

        return false;
    }
}
