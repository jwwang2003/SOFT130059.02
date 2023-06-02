package org.lab.gamelogic;


import org.lab.Constants;
import org.lab.gamelogic.entities.*;
import org.lab.gamelogic.movement.Direction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Map implements Serializable {
    static private List<List<Integer>> defaultPositions = new ArrayList<>();
    static private boolean hasValidMap = false;

    static private Position startPosition;
    static private Position finalPosition;

    private List<Position> mapPositions = new ArrayList<>();    // keeps track of movable objects
    public List<Position> _mapPositions = new ArrayList<>();

    private GameBoy gameBoy;

    public Map(List<Position> elements, Position finalPosition, GameBoy gameBoy) {
        this.mapPositions.addAll(elements);
        this.finalPosition = finalPosition;
        this.gameBoy = gameBoy;
    }

    public Map() {
        int i = 0, j = 0;

        for(List<Integer> row : defaultPositions) {
            for(Integer element : row) {

                Entity newEntity = null;

                switch (element) {
                    case 1:
                        newEntity = new Wall();
                        break;
                    case 2:
                        startPosition = new Position(i, j, true);
                        newEntity = gameBoy = new GameBoy();
                        break;
                    case 3:
                        finalPosition = new Position(i, j, true);
                        newEntity = new Goal();
                        break;
                    case 4:
                        newEntity = new Obstacle();
                        break;
                    case 5:
                        newEntity = new Box();
                        break;
                    default:
                        break;
                }

                if(newEntity != null) {
                    if(newEntity instanceof Movable) {
                        Position p = new Position(i, j, false);
                        newEntity.setPosition(p);
                        mapPositions.add(p);
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

        return;
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

    public GameBoy getGameBoy() { return this.gameBoy; }

    public void moveGameBoy(Direction dir) {
        boolean result = this.gameBoy.pushBy(this, null, dir);
    }

    public void removePos(Position pos) { this.mapPositions.remove(pos); }

    static public String readMapFromScanner(Scanner scanner, List<List<Integer>> map) {
//        map = new ArrayList<>();
        String text = "";

        int i = 0, j = 0;

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            text += line + "\n";

            String[] lineItems = line.split(" ");

            if(j == 0) map.add(new ArrayList<>());

            for(String s : lineItems) {
                int num = Character.getNumericValue(s.charAt(0));
                map.get(i).add(num);
                ++j;
            }

            ++i;
            j = 0;
        }

        return text;
    }

    public List<Position> getMapPositions() {
        return mapPositions;
    }

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
        String text = "";

        for(List<Integer> row : map)
            for(int i = 0; i < row.size(); ++i) {
                if(i != row.size() - 1) text += row.get(i) + " ";
                else text += row.get(i) + "\n";
            }

        return text;
    }

    static public String defaultPosition2String() {
        return map2String(defaultPositions);
    }

    public String toString(List<Position> customList) {
        List<List<Integer>> sMap = new ArrayList<>();
        for (int i = 0; i < Constants.canvasRowCount; ++i) {
            Integer[] temp = new Integer[Constants.canvasColCount];
            Arrays.fill(temp, 0);
            sMap.add(Arrays.asList(temp));
        }

        for(Position pos: customList) {
            Integer i = 0;
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
            }

            sMap.get(pos.getRow()).set(pos.getCol(), i);
        }

        return map2String(sMap);
    }

    @Override
    public String toString() {
        List<List<Integer>> sMap = new ArrayList<>();
        for (int i = 0; i < Constants.canvasRowCount; ++i) {
            Integer[] temp = new Integer[Constants.canvasColCount];
            Arrays.fill(temp, 0);
            sMap.add(Arrays.asList(temp));
        }

        for(Position pos: this.mapPositions) {
            Integer i = 0;
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
            }

            sMap.get(pos.getRow()).set(pos.getCol(), i);
        }

        return map2String(sMap);
    }
}
