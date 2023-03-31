package lab5;

/*
 * Libraries for input
 */
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Main {
  static final int numPlayers = 2;

  public static void main(String[] args) throws IOException {
    ArrayList<String> playerNames = Game.getPlayerNames(numPlayers);
    Game game = new Game();

    game.readTable();
    game.initPlayers(playerNames);

    while(game.runGame()) {
      // loop until all players cannot play anymore

    }

    game.printResults();

    return;
  }
}

// Game driver handles input too!
class Game {
  // static Scanner in = new Scanner(System.in);
  static Reader in = new Reader();

  private Map map;
  private ArrayList<Player> playerList; // contains refrences to hashMap
  private HashMap<String, Player> playerHashList;
  
  private Player prevPlayer = null;
  private String tempBuffer;

  private ArrayList<Player> winningPlayers;

  public Game() {
    map = new Map();
    playerList = new ArrayList<>();
    playerHashList = new HashMap<>();
    winningPlayers = new ArrayList<>();
  }

  // Static functions
  public static ArrayList<String> getPlayerNames(int n) throws IOException {
    ArrayList<String> names = new ArrayList<>();
    String s[] = in.readLine().split(" ");

    for(int i = 0; i < s.length; ++i) {
        names.add(s[i]);
    }

    return names;
  }

  public void initPlayers(ArrayList<String> names) {
    for (String name : names) {
      Player p = new Player(name);
      playerList.add(p);
      playerHashList.put(name, p);
    }
  }

  public void readTable() throws IOException {
    ArrayList<ArrayList<Entity>> grid = new ArrayList<ArrayList<Entity>>();
    
    // initial values
    int startX = 0, startY = 0;
    int winX = 0, winY = 0;
    int i = 0;
    
    while (true) {
      // char s = in.next().charAt(0);
      String input = in.readLine();
      String s[] = input.split(" ");

      if (!Character.isDigit(input.charAt(0))) {
        tempBuffer = input;
        map.setMap(grid);

        Player.setWinX(winX);
        Player.setWinY(winY);

        Player.setStartX(startX);
        Player.setStartY(startY);

        return;
      }

      grid.add(new ArrayList<Entity>());

      for(int x = 0; x < s.length; ++x) {
        if(s[x].charAt(0) == '0') {
          grid.get(i).add(null);
        }
        if(s[x].charAt(0) == '1') {
          grid.get(i).add(new Wall());
        }
        if (s[x].charAt(0) == '2') {
          startX = i;
          startY = grid.get(i).size();
          grid.get(i).add(null);
        } else if (s[x].charAt(0) == '3') {
          winX = i;
          winY = grid.get(i).size();
          grid.get(i).add(null);
        }
        else if (s[x].charAt(0) == '4') {
          grid.get(i).add(new Blockade());
        }
        else {
          // invalid input
          // no need to handle
        }
      }
      ++i;
    }
  }

  public boolean runGame() throws IOException {
    String processed[] = tempBuffer.split(" ");
    String playerName = processed[0];
    char move = processed[1].charAt(0);

    // Select player
    Player p = playerHashList.get(playerName);

    if(Player.getRemainingPlayers() == 1) {
      prevPlayer = null;
    }

    if(p != prevPlayer && p.canPlay()) {
      switch (move) {
        case 'h':
        case 'j':
        case 'k':
        case 'l':
          int m = Player.Move.valueOf(Character.toString(move)).ordinal();
          this.move(m, p);
          // set prevPlayer to current player after a successful move
          prevPlayer = p;
          break;
        case 'q':
          p.forfeit();
        case '\n':
        case ' ':
          break;
        default:
          p.invokedInvalidKeystroke();
          break;
      }
    } else {
      p.invokedWrongMove();
    }

    // SUB CHECKS
    if(Player.getRemainingPlayers() == 0) return false;
    if(p.hasWon()) winningPlayers.add(p);

    // map.printMap();

    // read another player's data
    tempBuffer = in.readLine();

    // return true for another round
    return true;
  }

  public void printResults() {
    for(Player p : playerList) {
      System.out.println(
        p.getName() + " " + p.getValidMoves() + " " + 
        p.getWrongMoves() + " " + p.getInvalidKeystrokes() + " " + 
        p.getOverlapMoves() + " " + p.getHitWallMoves()
      );
    }

    if(winningPlayers.isEmpty()) {
      System.out.println("draw");
      return;
    }

    if(winningPlayers.size() > 1) {
      Collections.sort(winningPlayers);
    }

    System.out.println(winningPlayers.get(0).getName());
  }

  private boolean move(int i, Player p) {
    int [] playerPos = p.getPos();
    int pX = playerPos[0], pY = playerPos[1];
    int dX = pX + Player.d[i][0], dY = pY + Player.d[i][1];
    // System.out.println(dX + " " + dY);

    // if(!map.isBound(dX, dY)) return false;
    // if(map.isWall(dX, dY) || map.isBlockade(dX, dY)) return false;
    if (map.isWall(dX, dY)) {
      p.invokedHitWallMove();
      p.invokedValidMove();
      return false;
    }

    if (map.isBlockade(dX, dY)) {
      // overlapped with blockade
      p.invokedOverlapMove();
    }

    p.setPos(dX, dY);

    return true;
  }
}

class Map {
  private ArrayList<ArrayList<Entity>> grid;

  public Map() {
    grid = new ArrayList<>();
  }

  public void printMap() {
    for(int i = 0; i < grid.size(); ++i) {
      for(int j = 0; j < grid.get(i).size(); ++j) {
        Entity e = grid.get(i).get(j);
        System.out.print( e != null ? e.getType() : 0);
      }
      System.out.println();
    }
  }

  public int getPos(int x, int y) {
    if(!isBound(x, y)) return -1;

    Entity e = grid.get(x).get(y);

    return e != null ? e.getType() : 0;
  }

  public boolean isEmpty(int x, int y) { return getPos(x, y) == 0; }
  public boolean isWall(int x, int y) { return getPos(x, y) == 1; }
  public boolean isBlockade(int x, int y) { return getPos(x, y) == 4; }

  public boolean isBound(int x, int y) { return grid.size() > x && x >= 0 && grid.get(x) != null && (grid.get(x).size() > y && y >= 0); }

  public void setMap(ArrayList<ArrayList<Entity>> map) { grid = map; }
}

// 墙
class Wall extends Entity {
  public Wall() { this.setType(1); }
}

// 障碍物
class Blockade extends Entity {
  public Blockade() { this.setType(4); }
}

/* 
 * 角色昵称   角色的合法移动次数  错误移动次数      错误输入指令数           与障碍物重叠的次数 碰墙的次数
 * playName  validMoveCount   wrongMoveCount  invalidKeystrokeCount overlapCount    hitWallCount
 * 
 */

class Player extends Entity implements Comparable<Player> {
  public final static int[][] d = {
    {0, -1},  // LEFT
    {1, 0},   // DOWN
    {-1, 0},  // UP
    {0, 1},   // RIGHT
  };

  public static enum Move {
    h,  // LEFT
    j,  // DOWN
    k,  // UP
    l,  // RIGHT
  };

  private String name;

  private int validMoveCount, wrongMoveCount,
              invalidKeystrokeCount,
              overlapCount, hitWallCount;

  private static int startX, startY;
  private static int winX, winY;
  private boolean won, quit;

  private static int remainingPlayers = 0;

  public Player(String name) {
    // Warning: x is UP DOWN, y is LEFT RIGHT
    this.name = name;
    setType(2); // Player is type 2
    
    // this.setPos(x, y);

    validMoveCount = 0;
    wrongMoveCount = 0;
    invalidKeystrokeCount = 0;
    overlapCount = 0;
    hitWallCount = 0;

    setX(startX);
    setY(startY);

    won = false;
    quit = false;

    ++remainingPlayers;
  }

  public void won() {
    if(quit || won) return;

    assert(!quit);

    int pos[] = getPos();
    assert(pos[0] == winX && pos[1] == winY);

    --remainingPlayers;

    won = true;
  }

  public boolean forfeit() {
    if(quit || won) return false;

    --remainingPlayers;

    return quit = !quit;
  }

  public boolean canPlay() {
    // the player can play if it has not won or quitted

    return !(won || quit);
  }

  public boolean hasWon() { return won; }

  public static int getRemainingPlayers() { return remainingPlayers; }

  public void invokedValidMove() { ++validMoveCount; }
  public void invokedWrongMove() { ++wrongMoveCount; }
  public void invokedInvalidKeystroke() { ++invalidKeystrokeCount; }
  public void invokedOverlapMove() { ++overlapCount; }
  public void invokedHitWallMove() { ++hitWallCount; }

  // Setter Methods

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setPos(int x, int y) {
    int dX = Math.abs(this.getX() - x);
    int dY = Math.abs(this.getY() - y);

    assert(dX <= 1);
    assert(dY <= 1);

    this.setX(x);
    this.setY(y);

    // check if new position is the final position
    if(this.getX() == winX && this.getY() == winY) {
      won();
    }

    invokedValidMove();

    return;
  }

  // Getter Methods

  public String getName() {
    return name;
  }

  public int getValidMoves() { return validMoveCount; }
  public int getWrongMoves() { return wrongMoveCount; }
  public int getInvalidKeystrokes() { return invalidKeystrokeCount; }
  public int getOverlapMoves() { return overlapCount; }
  public int getHitWallMoves() { return hitWallCount; }

  public int getScore() { return validMoveCount + overlapCount + hitWallCount; }

  // Static stuff
  public static void setWinX(int x) {
    winX = x;
  }
  
  public static void setWinY(int y) {
    winY = y;
  }

  public static void setStartX(int x) {
    startX = x;
  }

  public static void setStartY(int y) {
    startY = y;
  }

  // Comparator
  @Override
  public int compareTo(Player p) {
    return this.getScore() - p.getScore();
  }
}

class Entity {
  private int x = 0, y = 0;
  private int type = 0;

  // Setter methods
  
  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setPos(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Getter methods

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int[] getPos() {
    return (new int[]{x, y});
  }

  public int getType() {
    return type;
  }

  public void setType(int x) {
    type = x;
  }
}

// Custom reader class
class Reader {
  final private int BUFFER_SIZE = 1 << 16;
  private DataInputStream din;
  private byte[] buffer;
  private int bufferPointer, bytesRead;

  public Reader() {
    din = new DataInputStream(System.in);
    buffer = new byte[BUFFER_SIZE];
    bufferPointer = bytesRead = 0;
  }

  public Reader(String file_name) throws IOException {
    din = new DataInputStream(
        new FileInputStream(file_name));
    buffer = new byte[BUFFER_SIZE];
    bufferPointer = bytesRead = 0;
  }

  public String readLine() throws IOException {
    byte[] buf = new byte[64]; // line length
    int cnt = 0, c;
    while ((c = read()) != -1) {
      if (c == '\n') {
        if (cnt != 0) {
          break;
        } else {
          continue;
        }
      }
      buf[cnt++] = (byte) c;
    }
    return new String(buf, 0, cnt);
  }

  public int nextInt() throws IOException {
    int ret = 0;
    byte c = read();
    while (c <= ' ') {
      c = read();
    }
    boolean neg = (c == '-');
    if (neg)
      c = read();
    do {
      ret = ret * 10 + c - '0';
    } while ((c = read()) >= '0' && c <= '9');

    if (neg)
      return -ret;
    return ret;
  }

  public long nextLong() throws IOException {
    long ret = 0;
    byte c = read();
    while (c <= ' ')
      c = read();
    boolean neg = (c == '-');
    if (neg)
      c = read();
    do {
      ret = ret * 10 + c - '0';
    } while ((c = read()) >= '0' && c <= '9');
    if (neg)
      return -ret;
    return ret;
  }

  public double nextDouble() throws IOException {
    double ret = 0, div = 1;
    byte c = read();
    while (c <= ' ')
      c = read();
    boolean neg = (c == '-');
    if (neg)
      c = read();

    do {
      ret = ret * 10 + c - '0';
    } while ((c = read()) >= '0' && c <= '9');

    if (c == '.') {
      while ((c = read()) >= '0' && c <= '9') {
        ret += (c - '0') / (div *= 10);
      }
    }

    if (neg)
      return -ret;
    return ret;
  }

  private void fillBuffer() throws IOException {
    bytesRead = din.read(buffer, bufferPointer = 0,
        BUFFER_SIZE);
    if (bytesRead == -1)
      buffer[0] = -1;
  }

  private byte read() throws IOException {
    if (bufferPointer == bytesRead)
      fillBuffer();
    return buffer[bufferPointer++];
  }

  public void close() throws IOException {
    if (din == null)
      return;
    din.close();
  }
}