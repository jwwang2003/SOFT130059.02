package com.example.lab10.gamelogic;

import com.example.lab10.gamelogic.entities.GameBoy;
import com.example.lab10.gamelogic.movement.Direction;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.List;

public class PlayerSession implements Serializable {
    private String username;
    private Map gameMap;
    private SerializableColor playerColor;

    public PlayerSession(String username, Color playerColor) {
        this.username = username;
        this.gameMap = new Map();
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
        this.gameMap.moveGameBoy(dir);
//        if (isFinished())
//            throw new RuntimeException("can't run finished session");
//        System.out.println("player " + playerName + ", move dir:" + dir);
//        MoveResult result = map.moveGameBoy(dir);
//        totalMovements++;
//        if (!result.isMoveSuccess())
//            invalidMovements++;
    }
}
