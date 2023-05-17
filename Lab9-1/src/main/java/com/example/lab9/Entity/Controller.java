package com.example.lab9.Entity;

import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/16 21:25
 */

public class Controller {
    private static List<Element[]> map;

    private static Player player;
    static EntityIcons playerIcon = new EntityIcons("src/main/resources/com/example/lab9/img/character.png", 100, 100);
    static EntityIcons emptyIcon = new EntityIcons("src/main/resources/com/example/lab9/img/empty.png", 100, 100);
    static EntityIcons wallIcon = new EntityIcons("src/main/resources/com/example/lab9/img/wall.png", 100, 100);

    public static void readMap(String path){
        map = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            int y_axis = 0;



            while ((str = in.readLine()) != null) {
                String[] row = str.split(" ");
                Element[] elements = new Element[row.length];
                for (int x_axis = 0; x_axis < row.length; x_axis++) {
                    /* 
                     * WRITE YOUR CODE HERE 
                    */

                    switch(row[x_axis]) {
                        case "0":
                            elements[x_axis] = new Empty(emptyIcon);
                            break;
                        case "1":
                            elements[x_axis] = new Wall(wallIcon);
                            break;
                        case "2":
                            elements[x_axis] = new Empty(emptyIcon);
                            player = new Player(x_axis, y_axis, playerIcon);
                            break;
                        default:
                            break;
                    }
                }
                map.add(elements);
                y_axis++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void drawMap(GraphicsContext gc) {
        /*
         * WRITE YOUR CODE HERE
         * 
         * you may use class GraphicsContext's method drawImage()
         *  
         */
        int x = 0, y = 0;
        for(Element[] elements : map) {
            for(Element element : elements) {
                EntityIcons ei = element.getEntityIcons();
                gc.drawImage(ei.getImage(), x*100, y*100, 100, 100);
                x++;
            }
            x = 0;
            y++;
        }

        gc.drawImage(player.getEntityIcons().getImage(), player.getPos_x()*100, player.getPos_y()*100);
    }

    public static boolean click(int x, int y){
        /*
         * WRITE YOUR CODE HERE
         */
        Element[] elements = map.get(y);
        Element e = elements[x];

        if(e instanceof Empty) {
            int dX = Math.abs(x - player.getPos_x());
            int dY = Math.abs(y - player.getPos_y());
            if ((dX == 1 && dY == 0) || (dX == 0 && dY == 1)) {
                player = new Player(x, y, playerIcon);
                return true;
            }
        }

        return false;
    }
}
