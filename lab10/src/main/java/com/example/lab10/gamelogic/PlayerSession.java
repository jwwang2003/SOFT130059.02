package com.example.lab10.gamelogic;

import com.example.lab10.gamelogic.entities.Box;
import com.example.lab10.gamelogic.entities.GameBoy;
import com.example.lab10.gamelogic.movement.Direction;
import com.example.lab10.model.GameHolder;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.io.Serializable;

import java.util.List;

public class PlayerSession implements Serializable {
    private SubmissionPublisher<Boolean> publisher = new SubmissionPublisher<>();

    private String username = "";
    private Map gameMap;
    private List<PlayerSession> gameHistory;

    public Runnable pushHistoryCallback = () -> {
        this.gameHistory.add(this);
    };

    public Runnable rewindMapCallback = () -> {
        int ind = gameHistory.size() - 1;
        if(ind >= 0) {
            int index = GameHolder.getInstance().playerSessionList.indexOf(this);
            GameHolder.getInstance().playerSessionList.set(index, gameHistory.remove(gameHistory.size() - 2));
        }
    };

    private SerializableColor playerColor;
    private boolean gameEnd = false;

    public PlayerSession(String username, Color playerColor) {
        this.username = username;
        this.gameMap = new Map(this);
        this.gameHistory = new ArrayList<>(Arrays.asList(this));
        this.playerColor = new SerializableColor(playerColor);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map getGameMap() { return this.gameMap; }
    public GameBoy getGameBoy() { return this.gameMap.getGameBoy(); }

    public Color getPlayerColor() { return this.playerColor.toJavaFXColor(); }

    public void move(Direction dir) {
        if(this.gameEnd) {
//            throw new RuntimeException("Session has finished!");
//            System.exit(0);
        }
        this.gameMap.moveGameBoy(dir);
//        if (isFinished())
//            throw new RuntimeException("can't run finished session");
//        System.out.println("player " + playerName + ", move dir:" + dir);
//        MoveResult result = map.moveGameBoy(dir);
//        totalMovements++;
//        if (!result.isMoveSuccess())
//            invalidMovements++;
    }

    public boolean checkGameEnd() {
        Position finalPosition = this.gameMap.finalPosition;
        List<Box> boxList = this.gameMap.getBoxEntities();

        for(Box box : boxList) {
            if(!(box.getPosition().equals(finalPosition))) {
                publisher.submit(this.gameEnd = false);
                return this.gameEnd;
            }
        }

        publisher.submit(this.gameEnd = true);
        publisher.close();
        return this.gameEnd;
    }

    public void registerCompletionSubscriber(Flow.Subscriber<Boolean> subscriber) {
        publisher.subscribe(subscriber);
    }
}
