package com.example.lab9.Entity;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/17 10:35
 */

public class Player extends Element{
    private int pos_x;
    private int pos_y;

    public Player(int pos_x, int pos_y, EntityIcons entityIcons) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.entityIcons = entityIcons;
    }

    public EntityIcons getEntityIcons() {
        return entityIcons;
    }

    public void setEntityIcons(EntityIcons entityIcons) {
        this.entityIcons = entityIcons;
    }

    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }
}
