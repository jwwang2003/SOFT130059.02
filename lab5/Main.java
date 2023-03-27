package lab5;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
  static Reader in = new Reader();

  public static void main(String[] args) throws IOException {
    Game game = new Game(in, 2);

    String c = game.readTable(in);

    while(true) {
      System.out.println("TEST");
      char C = (char)in.read();
      System.out.println(c + " " + C);
      c = in.nextString();
    }

    // return;
  }


}

class Game {
  private Map map;
  private ArrayList<Player> players;

  public Game(Reader in, int numPlayers) throws IOException {
    map = new Map();
    players = new ArrayList<>();

    for(int i = 0; i < numPlayers; ++i) {
      String playerName = in.nextString();
      players.add(new Player(playerName));
    }
  }

  public String readTable(Reader in) throws IOException {
    ArrayList<ArrayList<Entity>> grid = new ArrayList<ArrayList<Entity>>();
    
    // initial values
    int playerX = 0, playerY = 0;
    int winX = 0, winY = 0;
    int i = 0;
    int c = 0;
    
    while (true) {
      char s = (char) in.read();

      if (s == ' ')
        continue;
      if (s == '\n') {
        ++i;
        c = 0;
        continue;
      }

      if (!Character.isDigit(s)) {
        String temp = in.nextString();
        map.setMap(grid);
        Player.setWinX(winX);
        Player.setWinY(winY);

        for(Player p : players) {
          p.setPos(playerX, playerY);
        }

        return s + temp;
      }

      if (c == 0)
        grid.add(new ArrayList<Entity>());

      if(s == '0') {
        grid.get(i).add(null);
      }
      if(s == '1') {
        grid.get(i).add(new Wall());
      }
      if (s == '2') {
        playerX = i;
        playerY = grid.get(i).size();
        grid.get(i).add(null);
      } else if (s == '3') {
        winX = i;
        winY = grid.get(i).size();
        grid.get(i).add(null);
      }
      else if (s == '4') {
        grid.get(i).add(new Blockade());
      }
      else {
        // invalid input
      }

      c++;
    }
  }
}

class Map {
  private ArrayList<ArrayList<Entity>> grid;

  public Map() {
    grid = new ArrayList<>();
  }

  public int entityLookUp(Entity e) {
    if(e == null) return 0;
    if(e instanceof Wall) return 1;

    // 2 - START POINT
    // 3 - END POINT
    if(e instanceof Blockade) return 4;

    return -1;
  }

  public int getPos(int x, int y) {
    return this.entityLookUp(grid.get(x).get(y));
  }

  public boolean isEmpty(int x, int y) {
    return getPos(x, y) == 0;
  }

  public boolean isWall(int x, int y) {
    return getPos(x, y) == 1;
  }

  public boolean isBlockade(int x, int y) {
    return getPos(x, y) == 4;
  }

  public boolean isBound(int x, int y) {
    return grid.size() > x && x >= 0 && grid.get(x) != null && (grid.get(x).size() > y && y >= 0);
  }

  public void setMap(ArrayList<ArrayList<Entity>> map) {
    grid = map;
  }
}

class Blockade extends Entity {
  public Blockade() {

  }
}

class Wall extends Entity {
  public Wall() {

  }
}

class Player extends Entity{
  // displacement
  public final static int[][] d = {
    {0, -1},  // LEFT
    {1, 0},   // DOWN
    {-1, 0},  // UP
    {0, 1},   // RIGHT
  };

  public static enum Move {
    h,
    j,
    k,
    l,
  }

  private String name;

  private int validMovesC;
  private int errorMovesC;
  private int invalidKeystrokesC;
  private int overlapC;
  private int hitWallC;

  private static int winX, winY;
  private boolean won;
  private boolean quit;

  public Player(String name) {
    // Warning: x is UP DOWN, y is LEFT RIGHT
    winX = 1;
    this.name = name;
    
    // this.setPos(x, y);

    validMovesC = 0;
    errorMovesC = 0;
    invalidKeystrokesC = 0;
    overlapC = 0;
    hitWallC = 0;

    won = false;
    quit = false;
  }
  
  public void won() {
    assert(!quit);
    
    won = true;
  }

  public boolean forfeit() {
    if(quit || won) return false;

    return quit = !quit;
  }

  public boolean canPlay() {
    // the player can play if it has not won or quitted

    return !(won && quit);
  }
  
  public void moveInvoked() {
    ++validMovesC;
  }

  public void errorMoveInvoked() {
    ++errorMovesC;
  }
  
  public void invalidKeystrokeInvoked() {
    ++invalidKeystrokesC;
  }

  public void overlapMoveInvoked() {
    ++overlapC;
  }

  public void hitWallInvoked() {
    ++hitWallC;
  }

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
    if(x == winX && y == winY) {
      won();
    }

    moveInvoked();

    return;
  }

  // Getter Methods

  public String getName() {
    return name;
  }

  public int[] getPos() {
    int[] coord = { this.getX(), this.getY() };
    return coord;
  }

  public int getTotalMoves() {
    return validMovesC;
  }

  public int getTotalErrorMoves() {
    return errorMovesC;
  }

  public int getTotalInvalidKeystrokes() {
    return invalidKeystrokesC;
  }

  public int getTotalOverlapMoves() {
    return overlapC;
  }

  public int getTotalHitWall() {
    return hitWallC;
  }

  // Static stuff
  public static void setWinX(int x) {
    winX = x;
  }
  
  public static void setWinY(int y) {
    winY = y;
  }

  public static int getWinX() {
    return winX;
  }

  public static int getWinY() {
    return winY;
  }
}

class Entity {
  private int x = 0, y = 0;

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

  public String nextString() throws IOException {
    String s = "";

    byte c = read();
    while(c <= ' ') {
      c = read();
    }
    while(Character.isLetterOrDigit(c)) {
      s += (char)c;
      c = read();
    } 

    return s;
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

  public byte read() throws IOException {
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