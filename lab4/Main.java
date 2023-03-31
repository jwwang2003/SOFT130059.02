package lab4;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
  static Reader in = new Reader();

  public static void main(String[] args) throws IOException{
    Game game = new Game();
    
    char c = game.readTable();

    // for(int i = 0; i < game.map.grid.size(); ++i) {
    //   for(int j = 0; j < game.map.grid.get(i).size(); ++j) {
    //     System.out.print(game.map.grid.get(i).get(j));
    //   }
    //   System.out.println();
    // }

    while(c != 'q') {
      game.run(c);

      c = (char)in.read();
    }

    System.out.print(game.player.getMoveCounter() + " " + game.player.getFailedMoveCounter() + " " + game.player.getHitWallCounter() + " ");
    System.out.println("(" + game.player.x + "," + game.player.y + ")");
  }

  static class Map {
    private ArrayList<ArrayList<Integer>> grid;

    public Map() {
      grid = new ArrayList<>();
    }

    public int getPos(int x, int y) {
      return grid.get(x).get(y);
    }

    public boolean isWall(int x, int y) {
      return getPos(x, y) == 1;
    }

    public boolean isBound(int x, int y) {
      return grid.size() > x && x >= 0 && grid.get(x) != null && (grid.get(x).size() > y && y >= 0);
    }

    public void setMap(ArrayList<ArrayList<Integer>> map) {
      grid = map;
    }
  }

  static private class Player {
    private int x;
    private int y;
    private int moveCounter;
    private int invalidCounter;
    private int hitWallCounter;

    public Player(int x, int y) {
      this.x = x;
      this.y = y;
      moveCounter = 0;
      invalidCounter = 0;
      hitWallCounter = 0;
    }

    public void moved() {
      moveCounter++;
    }

    public void invalidInput() {
      invalidCounter++;
    }

    public void hitWall() {
      hitWallCounter++;
    }

    public void setPos(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int[] getPos() {
      int[] coord = { x, y };
      return coord;
    }

    public int getMoveCounter() {
      return moveCounter;
    }

    public int getFailedMoveCounter() {
      return invalidCounter;
    }

    public int getHitWallCounter() {
      return hitWallCounter;
    }
  }

  static public class Game {
    // displacement
    private int[][] d = {
      {0, -1},  // LEFT
      {1, 0},   // DOWN
      {-1, 0},  // UP
      {0, 1},   // RIGHT
    };
    private enum Move {
      h,
      j,
      k,
      l,
    }

    private Map map;
    private Player player;

    public Game() {
      map = new Map();
    }

    public boolean run(char c) {
      // System.out.println(">" + Move.valueOf(Character.toString(c)).ordinal());
      switch (c) {
        case 'h':
        case 'j':
        case 'k':
        case 'l':
          // System.out.println(">" + (Move.valueOf(Character.toString(c)).ordinal()));
          if(this.move(Move.valueOf(Character.toString(c)).ordinal())) {
            player.moved();
          } else {
            player.hitWall();
          }
          break;
        case 'q':
          // this code is actually unreachable
          return false;
        case '\n':
        case ' ':
          break;
        default:
          player.invalidInput();
          break;
      }

      return true;
    }

    private boolean move(int i) {
      int [] playerPos = player.getPos();
      int pX = playerPos[0], pY = playerPos[1];
      int dX = pX + d[i][0], dY = pY + d[i][1];
      // System.out.println(dX + " " + dY);

      // if(!map.isBound(dX, dY)) return false;
      if(map.isWall(dX, dY)) return false;

      player.setPos(dX, dY);

      return true;
    }

    public char readTable() throws IOException {
      ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();
      int playerX = 0, playerY = 0;
      int i = 0;
      int c = 0;
      
      while (true) {
        char s = (char) in.read();
  
        if (c == 0)
          grid.add(new ArrayList<Integer>());
  
        if (s == ' ')
          continue;
        if (s == '\n') {
          ++i;
          c = 0;
          continue;
        }
        if (!Character.isDigit(s)) {
          map.setMap(grid);
          player = new Player(playerX, playerY);
          return s;
        }
  
        if (s == '2') {
          playerX = i;
          playerY = grid.get(i).size();
          grid.get(i).add(0);
        } else {
          grid.get(i).add(Integer.parseInt(String.valueOf(s)));
        }
  
        c++;
      }
    }


  }

  // Custom reader class
  static class Reader {
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
}