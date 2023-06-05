package com.example.lab10.model;

import com.example.lab10.gamelogic.CompletionSubscriber;
import com.example.lab10.gamelogic.PlayerSession;
import com.example.lab10.gamelogic.Position;
import com.example.lab10.gamelogic.entities.GameBoy;

import java.util.ArrayList;
import java.util.List;

public class GameHolder {
    CompletionSubscriber completionSubscriber = new CompletionSubscriber();

    public List<PlayerSession> playerSessionList = new ArrayList<>();
    private int finishedSessions = 0;

    private boolean active = false;
    private Runnable onGameFinish;

    private final static GameHolder INSTANCE = new GameHolder();

    private GameHolder() {

    }

    public void setOnGameFinish(Runnable callback1) {
        this.onGameFinish = callback1;
    }
    public void addPlayerSession(PlayerSession playerSession) {
        this.playerSessionList.add(playerSession);
        playerSession.registerCompletionSubscriber(completionSubscriber);
    }

    public static GameHolder getInstance() {
        return INSTANCE;
    }

    public void sessionFinished() {
        this.finishedSessions++;
        if(finishedSessions == this.playerSessionList.size()) {
            this.active = false;
            onGameFinish.run();
        }
    }

    public void setActiveState(boolean bool) { this.active = bool; }

    public boolean getActive() {
        return this.active;
    }

    public boolean hasPlayer(Position pos) {
        for(PlayerSession pS: playerSessionList) {
            return pS.getGameMap().getElementAt(pos) instanceof GameBoy;
        }

        return false;
    }
}
