package org.lab.model;

import org.lab.gamelogic.PlayerSession;
import org.lab.gamelogic.Position;
import org.lab.gamelogic.entities.GameBoy;

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
