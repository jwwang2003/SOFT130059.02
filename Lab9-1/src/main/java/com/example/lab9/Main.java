package com.example.lab9;

import com.example.lab9.Entity.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/16 21:14
 */

public class Main extends Application {

    final static int entityWidth = 100;
    final static int entityHeight = 100;

    @Override
    public void start(Stage stage) {
        Controller.readMap("src/main/resources/com/example/lab9/map/map.txt");
        int width = 500;
        int height = 500;
        Canvas canvas = new Canvas(width, height);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        Controller.drawMap(gc);
        Pane pane = new Pane();
        Scene scene = new Scene(pane, width, height);
        scene.setOnMousePressed(mouseEvent -> {
            int[] clickPos = getClickPos(mouseEvent.getX(), mouseEvent.getY(), entityWidth, entityHeight);
            if (Controller.click(clickPos[1], clickPos[0])){
                Controller.drawMap(gc);
            }
        });
        pane.getChildren().add(canvas);
        stage.setTitle("Lab9");
        stage.setScene(scene);
        stage.show();
    }

    public static int[] getClickPos(double click_x, double click_y, int entityWidth, int entityHeight){
        int[] pos = new int[2];
        /*
         * WRITE YOUR CODE HERE
         */

//        System.out.println(click_x + " " + click_y + " " + entityWidth + " " + entityHeight);

        pos[0] = (int)click_y/entityHeight;
        pos[1] = (int)click_x/entityWidth;

        return pos;
    }



    public static void main(String[] args) {
        launch();
    }

}
