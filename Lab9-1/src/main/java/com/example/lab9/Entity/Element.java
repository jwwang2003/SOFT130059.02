package com.example.lab9.Entity;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/17 10:34
 */

public class Element {
    protected EntityIcons entityIcons;
    public EntityIcons getEntityIcons() {
        return entityIcons;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
