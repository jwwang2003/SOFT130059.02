package com.example.lab9.Entity;

import javafx.scene.image.Image;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/17 11:01
 */

public class EntityIcons {
    private int width;
    private int height;
    private Image image;

    public EntityIcons(String imagePath, int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new Image("file:" + imagePath, width, height, true, true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }
}
