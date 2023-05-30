package com.example.lab10.gamelogic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.example.lab10.gamelogic.entities.Box;
import com.example.lab10.gamelogic.entities.GameBoy;
import com.example.lab10.gamelogic.entities.Obstacle;
import com.example.lab10.gamelogic.entities.Wall;
import com.example.lab10.gamelogic.movement.Direction;
import java.util.Arrays;

class MapTest {
    @Test
    void move() {
        GameBoy gameBoy = new GameBoy(new Position(10, 10));

        Map map = new Map(
                Arrays.asList(
                ),
                new Position(0, 0),
                gameBoy
        );

        System.out.println("BEFORE:");
        System.out.println("GameBoy: " + gameBoy.getPosition());

        map.moveGameBoy(Direction.left);

        System.out.println("AFTER:");
        System.out.println("GameBoy: " + gameBoy.getPosition());
        assertEquals(gameBoy.getPosition(), new Position(10, 9));
    }
    @Test
    void pushBox() {
        GameBoy gameBoy = new GameBoy(new Position(10, 10));
        Box box = new Box(new Position(10, 9));

        Map map = new Map(
                Arrays.asList(
                        new Position(10, 9, Arrays.asList(box))
                ),
                new Position(0, 0),
                gameBoy
        );

        System.out.println("BEFORE:");
        System.out.println("GameBoy: " + gameBoy.getPosition());
        System.out.println("Box: " + box.getPosition());

        map.moveGameBoy(Direction.left);

        System.out.println("AFTER:");
        System.out.println("GameBoy: " + gameBoy.getPosition());
        System.out.println("Box: " + box.getPosition());

        assertEquals(gameBoy.getPosition(), new Position(10, 9));
        assertEquals(box.getPosition(), new Position(10, 8));
    }

    @Test
    void pushBoxAgainstWallTest() {
        GameBoy gameBoy = new GameBoy(new Position(10, 10));
        Box box = new Box(new Position(10, 8));
        Wall wall = new Wall(new Position(10, 7));

        Map map = new Map(
                Arrays.asList(
                        new Position(10, 8, Arrays.asList(box)),
                        new Position(10, 7, Arrays.asList(wall))
                ),
                new Position(0, 0),
                gameBoy
        );

        System.out.println("GameBoy: " + gameBoy.getPosition());
        System.out.println("Box: " + box.getPosition());
        System.out.println("Wall: " + wall.getPosition());

        map.moveGameBoy(Direction.right);
        assertEquals(gameBoy.getPosition(), new Position(10, 11));

        map.moveGameBoy(Direction.left);
        map.moveGameBoy(Direction.left);
        assertEquals(gameBoy.getPosition(), new Position(10, 9));

        map.moveGameBoy(Direction.left);
        assertEquals(gameBoy.getPosition(), new Position(10, 9));
    }

    @Test
    void pushBoxAgainstObstacle() {
        Position p1 = new Position(10, 10);
        Position p2 = new Position(10, 9);
        Position p3 = new Position(10, 8);
        GameBoy gameBoy = new GameBoy(p1);
        Box box = new Box(p2);
        Obstacle obstacle = new Obstacle(p3);

        Map map = new Map(
                Arrays.asList(
                        p2,
                        p3
                ),
                new Position(0, 0),
                gameBoy
        );

        System.out.println("BEFORE:");
        System.out.println("GameBoy: " + gameBoy.getPosition());
        System.out.println("Box: " + box.getPosition());
        System.out.println("Obstacle: " + obstacle.getPosition());

        map.moveGameBoy(Direction.left);

        System.out.println("AFTER:");
        System.out.println("GameBoy: " + gameBoy.getPosition());
        System.out.println("Box: " + box.getPosition());
        System.out.println("Obstacle: " + obstacle.getPosition());

        assertEquals(gameBoy.getPosition(), new Position(10, 10));
        assertEquals(box.getPosition(), new Position(10, 9));
    }

    @Test
    void pushBoxOnObstacle() {
        Position p1 = new Position(10, 10);
        Position p2 = new Position(10, 9);

        GameBoy gameBoy = new GameBoy();
        Obstacle obstacle = new Obstacle();

        gameBoy.setPosition(p1);
        obstacle.setPosition(p2);

        Map map = new Map(
                Arrays.asList(
                        p1,
                        p2
                ),
                new Position(0, 0),
                gameBoy
        );

        System.out.println("BEFORE:");
        System.out.println("GameBoy: " + gameBoy.getPosition());
        System.out.println("Box: " + obstacle.getPosition());

        map.moveGameBoy(Direction.left);

        System.out.println("AFTER:");
        System.out.println("GameBoy: " + gameBoy.getPosition());
        System.out.println("Box: " + obstacle.getPosition());

        System.out.println(map.getPosition(new Position(10, 9)).getString());

        assertEquals(gameBoy.getPosition(), new Position(10, 9));
        assertEquals(obstacle.getPosition(), new Position(10, 9));
    }
}