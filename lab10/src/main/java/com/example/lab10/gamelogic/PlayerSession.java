package com.example.lab10.gamelogic;

import com.example.lab10.gamelogic.entities.Box;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.paint.Color;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.io.Serializable;

import java.util.List;

public class PlayerSession implements Serializable {
    private final SubmissionPublisher<Boolean> publisher = new SubmissionPublisher<>();

    private final String username;
    private final Map gameMap;


    private final SerializableColor playerColor;
    private boolean gameEnd = false;

    public PlayerSession(String username, Color playerColor) {
        this.username = username;
        this.gameMap = new Map(this);
        this.playerColor = new SerializableColor(playerColor);
    }

    public String getUsername() {
        return this.username;
    }

    public Map getGameMap() { return this.gameMap; }

    public Color getPlayerColor() { return this.playerColor.toJavaFXColor(); }

    public void move(Direction dir) {
        if(this.gameEnd) {
            throw new RuntimeException("Session has finished!");
        }
        this.gameMap.moveGameBoy(dir);
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
