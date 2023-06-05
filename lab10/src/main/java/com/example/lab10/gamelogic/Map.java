package com.example.lab10.gamelogic;

import com.example.lab10.Constants;
import com.example.lab10.gamelogic.entities.*;
import com.example.lab10.gamelogic.movement.Direction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Map implements Serializable {
    static private List<List<Integer>> defaultPositions = new ArrayList<>();
    static private boolean hasValidMap = false;

    public Position startPosition;
    public Position finalPosition;

    private final List<Position> mapPositions = new ArrayList<>();    // keeps track of movable objects
    private final List<Box> boxEntities = new ArrayList<>();
    public List<Position> _mapPositions = new ArrayList<>();

    private GameBoy gameBoy;

    public Map(List<Position> elements, Position finalPosition, GameBoy gameBoy) {
        this.mapPositions.addAll(elements);
        this.finalPosition = finalPosition;
        this.gameBoy = gameBoy;
    }

    public Map(PlayerSession playerSession) {

        int i = 0, j = 0;

        for(List<Integer> row : defaultPositions) {
            for(Integer element : row) {

                Entity newEntity = null;

                switch (element) {
                    case 1 -> newEntity = new Wall();
                    case 2 -> {
                        startPosition = new Position(i, j, true);
                        newEntity = gameBoy = new GameBoy();
                    }
                    case 3 -> {
                        finalPosition = new Position(i, j, true);
                        newEntity = new Goal();
                    }
                    case 4 -> newEntity = new Obstacle();
                    case 5 -> newEntity = new Box();
                    default -> {
                    }
                }

                if(newEntity != null) {
                    newEntity.setPlayerSession(playerSession);
                    if(newEntity instanceof Movable) {
                        Position p = new Position(i, j, false);
                        newEntity.setPosition(p);
                        mapPositions.add(p);

                        if(newEntity instanceof Box) boxEntities.add((Box) newEntity);
                    } else {
                        Position p = new Position(i, j, true);
                        newEntity.setPosition(p);
                        _mapPositions.add(p);
                    }
                }

                ++j;
            }

            ++i;
            j = 0;
        }
    }

    public Entity getElementAt(Position pos) {
        Position position = this.getPosition(pos);

        if(position.getAllEntities().size() == 0) return new Space();
        return position.peak();
    }

    public Position getPosition(Position pos) {
        return this.getPosition(pos, false);
    }

    public Position getPosition(Position pos, boolean viewLimit) {
        Position position = (mapPositions.stream().filter(
                (_pos) -> _pos.equals(pos)
        ).findFirst().orElse(null));

        if(position == null && !viewLimit) {
            position = (_mapPositions.stream().filter(
                    (_pos) -> _pos.equals(pos)
            ).findFirst().orElse(null));
        }

        if(position != null) return position;

        position = new Position(pos.getRow(), pos.getCol(), false);
        mapPositions.add(position);

        return position;
    }

    public Entity popPosition(Position pos) {
        Position position = this.getPosition(pos);

        Entity entity = position.popEntity();

        if(entity != null) {
            if(position.peak() == null) mapPositions.remove(position);
            return entity;
        }

        return null;
    }

    public void moveGameBoy(Direction dir) {
        this.gameBoy.pushBy(this, null, dir);
    }

    static public String readMapFromScanner(Scanner scanner, List<List<Integer>> map) {
//        map = new ArrayList<>();
        StringBuilder text = new StringBuilder();

        int i = 0;

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            text.append(line).append("\n");

            String[] lineItems = line.split(" ");

            map.add(new ArrayList<>());

            for(String s : lineItems) {
                int num = Character.getNumericValue(s.charAt(0));
                map.get(i).add(num);
            }

            ++i;
        }

        return text.toString();
    }

    public List<Position> getMapPositions() {
        return mapPositions;
    }

    public List<Box> getBoxEntities() { return this.boxEntities; }

    static public boolean getHasValidMap() {
        return hasValidMap;
    }

    static public boolean setDefaultPositions(List<List<Integer>> map) {
        if(map.size() != Constants.canvasRowCount) return false;
        for(List<Integer> row : map) {
            if(row.size() != Constants.canvasColCount) return false;
            for(Integer i : row)
                if(i > 5 || i < 0) return false;
        }

        defaultPositions = map;
        return hasValidMap = true;
    }

    static public boolean setDefaultPositions(String s) {
        if(s.isEmpty()) return false;
        List<List<Integer>> map = new ArrayList<>();
        String[] rows = s.split("\n");
        for(String row : rows) {
            List<Integer> newRow = new ArrayList<>();
            String[] elements = row.split(" ");
            for(String e : elements) {
                newRow.add(Character.getNumericValue(e.charAt(0)));
            }
            map.add(newRow);
        }

        return setDefaultPositions(map);
    }

    static public String map2String(List<List<Integer>> map) {
        StringBuilder text = new StringBuilder();

        for(List<Integer> row : map)
            for(int i = 0; i < row.size(); ++i) {
                if(i != row.size() - 1) text.append(row.get(i)).append(" ");
                else text.append(row.get(i)).append("\n");
            }

        return text.toString();
    }

    static public String defaultPosition2String() {
        return map2String(defaultPositions);
    }

    @Override
    public String toString() {
        List<List<Integer>> sMap = new ArrayList<>();
        for (int i = 0; i < Constants.canvasRowCount; ++i) {
            Integer[] temp = new Integer[Constants.canvasColCount];
            Arrays.fill(temp, 0);
            sMap.add(Arrays.asList(temp));
        }

        for(Position pos: mapPositions) {
            int i;
            if(pos.peak() instanceof Space) {
                i = 0;
            } else if(pos.peak() instanceof Wall) {
                i = 1;
            } else if(pos.peak() instanceof GameBoy) {
                i = 6;
            } else if(pos.peak() instanceof Obstacle) {
                i = 4;
            } else if(pos.peak() instanceof Box) {
                i = 5;
            } else { i = 0; }

            sMap.get(pos.getRow()).set(pos.getCol(), i);
        }

        return map2String(sMap);
    }
}
