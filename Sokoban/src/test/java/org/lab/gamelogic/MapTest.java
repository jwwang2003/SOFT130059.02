package org.lab.gamelogic;

import org.junit.jupiter.api.Test;
import org.lab.Main;
import org.lab.gamelogic.entities.GameBoy;
import org.lab.gamelogic.movement.Direction;
import org.lab.model.GameHolder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MapTest {
    @Test
    void testMove() throws URISyntaxException, IOException {
        Scanner read = new Scanner(new File(
                Main.class.getResource("defaultMap.txt").toURI()
        ), StandardCharsets.UTF_8);

        String text = "";
        List<List<Integer>> map = new ArrayList<>();
        text = Map.readMapFromScanner(read, map);

        Map.setDefaultPositions(map);

        GameHolder gameHolder = GameHolder.getInstance();
        PlayerSession playerSession = new PlayerSession("Test");

        gameHolder.playerSessionList.add(playerSession);

        GameBoy gameBoy = playerSession.getGameBoy();
        Map gameMap = playerSession.getGameMap();

        gameMap.moveGameBoy(Direction.up);
        gameMap.moveGameBoy(Direction.up);
        gameMap.moveGameBoy(Direction.right);
        gameMap.moveGameBoy(Direction.left);
        gameMap.moveGameBoy(Direction.left);

        gameMap.moveGameBoy(Direction.up);
        gameMap.moveGameBoy(Direction.up);
        gameMap.moveGameBoy(Direction.up);

        System.out.println(gameBoy.position);
        System.out.println(gameMap.getPosition(new Position(6, 2,  false)).peak());
        System.out.println(playerSession.getGameMap());
        System.out.println(gameMap.toString(gameMap._mapPositions));
    }


//    @Test
//    void move() {
//        GameBoy gameBoy = new GameBoy(new Position(10, 10));
//
//        Map map = new Map(
//                Arrays.asList(
//                ),
//                new Position(0, 0),
//                gameBoy
//        );
//
//        System.out.println("BEFORE:");
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//
//        map.moveGameBoy(Direction.left);
//
//        System.out.println("AFTER:");
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//        assertEquals(gameBoy.getPosition(), new Position(10, 9));
//    }
//    @Test
//    void pushBox() {
//        GameBoy gameBoy = new GameBoy(new Position(10, 10));
//        Box box = new Box(new Position(10, 9));
//
//        Map map = new Map(
//                Arrays.asList(
//                        new Position(10, 9, Arrays.asList(box))
//                ),
//                new Position(0, 0),
//                gameBoy
//        );
//
//        System.out.println("BEFORE:");
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//        System.out.println("Box: " + box.getPosition());
//
//        map.moveGameBoy(Direction.left);
//
//        System.out.println("AFTER:");
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//        System.out.println("Box: " + box.getPosition());
//
//        assertEquals(gameBoy.getPosition(), new Position(10, 9));
//        assertEquals(box.getPosition(), new Position(10, 8));
//    }
//
//    @Test
//    void pushBoxAgainstWallTest() {
//        GameBoy gameBoy = new GameBoy(new Position(10, 10));
//        Box box = new Box(new Position(10, 8));
//        Wall wall = new Wall(new Position(10, 7));
//
//        Map map = new Map(
//                Arrays.asList(
//                        new Position(10, 8, Arrays.asList(box)),
//                        new Position(10, 7, Arrays.asList(wall))
//                ),
//                new Position(0, 0),
//                gameBoy
//        );
//
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//        System.out.println("Box: " + box.getPosition());
//        System.out.println("Wall: " + wall.getPosition());
//
//        map.moveGameBoy(Direction.right);
//        assertEquals(gameBoy.getPosition(), new Position(10, 11));
//
//        map.moveGameBoy(Direction.left);
//        map.moveGameBoy(Direction.left);
//        assertEquals(gameBoy.getPosition(), new Position(10, 9));
//
//        map.moveGameBoy(Direction.left);
//        assertEquals(gameBoy.getPosition(), new Position(10, 9));
//    }
//
//    @Test
//    void pushBoxAgainstObstacle() {
//        Position p1 = new Position(10, 10);
//        Position p2 = new Position(10, 9);
//        Position p3 = new Position(10, 8);
//        GameBoy gameBoy = new GameBoy(p1);
//        Box box = new Box(p2);
//        Obstacle obstacle = new Obstacle(p3);
//
//        Map map = new Map(
//                Arrays.asList(
//                        p2,
//                        p3
//                ),
//                new Position(0, 0),
//                gameBoy
//        );
//
//        System.out.println("BEFORE:");
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//        System.out.println("Box: " + box.getPosition());
//        System.out.println("Obstacle: " + obstacle.getPosition());
//
//        map.moveGameBoy(Direction.left);
//
//        System.out.println("AFTER:");
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//        System.out.println("Box: " + box.getPosition());
//        System.out.println("Obstacle: " + obstacle.getPosition());
//
//        assertEquals(gameBoy.getPosition(), new Position(10, 10));
//        assertEquals(box.getPosition(), new Position(10, 9));
//    }
//
//    @Test
//    void pushBoxOnObstacle() {
//        Position p1 = new Position(10, 10);
//        Position p2 = new Position(10, 9);
//
//        GameBoy gameBoy = new GameBoy();
//        Obstacle obstacle = new Obstacle();
//
//        gameBoy.setPosition(p1);
//        obstacle.setPosition(p2);
//
//        Map map = new Map(
//                Arrays.asList(
//                        p1,
//                        p2
//                ),
//                new Position(0, 0),
//                gameBoy
//        );
//
//        System.out.println("BEFORE:");
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//        System.out.println("Box: " + obstacle.getPosition());
//
//        map.moveGameBoy(Direction.left);
//
//        System.out.println("AFTER:");
//        System.out.println("GameBoy: " + gameBoy.getPosition());
//        System.out.println("Box: " + obstacle.getPosition());
//
//        System.out.println(map.getPosition(new Position(10, 9)).getString());
//
//        assertEquals(gameBoy.getPosition(), new Position(10, 9));
//        assertEquals(obstacle.getPosition(), new Position(10, 9));
//    }
}