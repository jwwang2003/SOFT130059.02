package org.lab.gamelogic;

import org.lab.gamelogic.entities.GameBoy;
import org.lab.gamelogic.movement.Direction;

import java.io.Serializable;

public class PlayerSession implements Serializable {
    private String username;
    private Map gameMap;

    public PlayerSession(String username) {
        this.username = username;
        this.gameMap = new Map();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map getGameMap() { return this.gameMap; }
    public GameBoy getGameBoy() { return this.gameMap.getGameBoy(); }

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
