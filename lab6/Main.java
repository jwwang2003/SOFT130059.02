package lab6;

/*
 * Libraries for input
 */
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
  static final int numPlayers = 2;

  public static void main(String[] args) throws IOException {
    ArrayList<String> userNames = Game.getUserNames(numPlayers);
    Game game = new Game();

    game.readTable();
    game.initUsers(userNames);

    while(game.runGame()) {
      // loop until all players cannot play anymore

    }

    game.printResults();

    return;
  }
}

class Game {
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

  static Reader in = new Reader();

  private ArrayList<User> userList; // contains refrences to hashMap
  private HashMap<String, User> userHashMap;
  
  private User prevUser = null;
  private String tempBuffer;

  private ArrayList<User> winningUsers;

  public Game() {
    userList = new ArrayList<>();
    userHashMap = new HashMap<>();
    winningUsers = new ArrayList<>();
  }

  public static ArrayList<String> getUserNames(int n) throws IOException {
    ArrayList<String> names = new ArrayList<>();
    String s[] = in.readLine().split(" ");

    for(int i = 0; i < s.length; ++i) {
        names.add(s[i]);
    }

    return names;
  }

  public void initUsers(ArrayList<String> names) {
    for (String name : names) {
      User p = new User(name);
      userList.add(p);
      userHashMap.put(name, p);
    }
  }

  public void readTable() throws IOException {
    ArrayList<ArrayList<Integer>> grid = new ArrayList<ArrayList<Integer>>();
    int maxX = 0, maxY = 0;
    int startX = 0, startY = 0;
    int winX = 0, winY = 0;
    int i = 0;
    
    while (true) {
      String input = in.readLine();
      String s[] = input.split(" ");

      maxX = Math.max(maxX, i);
      maxY = Math.max(maxY, s.length);
      
      if (!Character.isDigit(input.charAt(0))) {
        tempBuffer = input;
        Map.setTemplateGrid(grid, maxX, maxY);

        User.setStartX(startX);
        User.setStartY(startY);
        User.setWinX(winX);
        User.setWinY(winY);

        break;
      }

      grid.add(new ArrayList<Integer>());
      
      ArrayList<Integer> row = grid.get(i);

      for(int x = 0; x < s.length; ++x) {
        Character c = s[x].charAt(0);
        int num = Character.getNumericValue(c);

        switch(num) {
          case 2:
            startX = i;
            startY = row.size();
            break;
          case 3:
            winX = i;
            winY = row.size();
            break;
          default:
            break;
        }

        row.add(num);
      }
      ++i;
    }
  }

  public boolean runGame() throws IOException {
    String processed[] = tempBuffer.split(" ");
    String playerName = processed[0];
    char move = processed[1].charAt(0);

    // Select player
    User user = userHashMap.get(playerName);
    Player player = user.getPlayer();
    int playerPos[] = player.getPos();

    if(User.getRemainingPlayers() == 1) {
      prevUser = null;
    }

    if(user != prevUser && user.canPlay()) {
      switch (move) {
        case 'h':
        case 'j':
        case 'k':
        case 'l':
          int m = Move.valueOf(Character.toString(move)).ordinal();
          this.move(m, user, playerPos[0], playerPos[1]);
          // set prevPlayer to current player after a successful move
          prevUser = user;
          break;
        case 'q':
          user.forfeit();
        case '\n':
        case ' ':
          break;
        default:
          user.invokedInvalidKeystroke();
          break;
      }
    } else {
      user.invokedWrongMove();
    }
    
    // SUB CHECKS
    user.evaluateWon();
    if(user.hasWon()) winningUsers.add(user);
    if(User.getRemainingPlayers() == 0) return false;
    
    // map.printMap();

    // read another player's data
    tempBuffer = in.readLine();

    // return true for another round
    return true;
  }

  public void printResults() {
    for(User p : userList) {
      System.out.println(
        p.getName() + " " + p.getValidMoves() + " " + 
        p.getWrongMoves() + " " + p.getInvalidKeystrokes() + " " + 
        p.getHitWallMoves() + " " + p.getOverlapMoves() + " " +
        p.getBoxWallCollisions() + " " + p.getBoxBlockadeCollisions()
      );
    }

    if(winningUsers.isEmpty()) {
      System.out.println("draw");
      return;
    }

    if(winningUsers.size() > 1) {
      Collections.sort(winningUsers);
      // Collections.reverse(winningPlayers);
    }

    System.out.println(winningUsers.get(0).getName());
  }

  private boolean move(int i, User user, int x, int y) {
    Map map = user.getMap();
    Stack<Entity> entityStack = map.getStack(x, y);
    int dX = x + d[i][0];
    int dY = y + d[i][1];

    if(entityStack.peek() instanceof Player) {
      user.invokedValidMove();

      if (map.isWall(dX, dY)) {
        user.invokedHitWallMove();
        return false;
      }

      if (map.isBlockade(dX, dY)) {
        // overlapped with blockade
        user.invokedOverlapMove();
      }
      if(map.isBox(dX, dY)) {
        if(!move(i, user, dX, dY)) {
          return false;
        }
      }
    } else if (entityStack.peek() instanceof Box) {
      if (map.isWall(dX, dY)) {
        user.invokedBoxWallCollision();
        return false;
      }

      if(map.isBlockade(dX, dY)) {
        user.invokedBoxBlockadeCollision();
        return false;
      }
    }

    Stack<Entity> curStack = map.getStack(x, y);
    Stack<Entity> destStack = map.getStack(dX, dY);
    destStack.push(curStack.pop());

    user.getPlayer().setPos(dX, dY);

    return true;
  }
}

/* 
 * 角色昵称   角色的合法移动次数  错误移动次数      错误输入指令数           角色碰墙的次数   与障碍物重叠的次数      箱子碰墙次数              箱子碰障碍物次数
 * playName  validMoveCount   wrongMoveCount  invalidKeystrokeCount hitWallCount    overlapCount        boxWallCollisionCount   boxBlockadeCollisionCount
 * 
 */

// Each user should have their own map to play on
class User implements Comparable<User> {
  private String name;

  private int validMoveCount, wrongMoveCount,
              invalidKeystrokeCount,
              hitWallCount, overlapCount,
              boxWallCollisionCount, boxBlockadeCollisionCount;

  private static int startX, startY;
  private static int winX, winY;
  private boolean won, quit;

  private static int remainingPlayers = 0;

  private Map map;
  private Player player;

  public User(String name) {
    // Caution: x is UP DOWN, y is LEFT RIGHT
    this.name = name;

    validMoveCount = 0;
    wrongMoveCount = 0;
    invalidKeystrokeCount = 0;
    overlapCount = 0;
    hitWallCount = 0;

    won = false;
    quit = false;

    ++remainingPlayers;

    // initialize the map

    map = new Map();
    player = new Player(User.startX, User.startY);

    map.placePlayer(player, startX, startY);
  }

  public void evaluateWon() {
    if(quit || won) return;

    assert(!quit);

    Stack<Entity> entityStack = map.getStack(winX, winY);
    if(entityStack.peek() instanceof Box) {
      won = true;

      --remainingPlayers;
    }
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
  public void invokedBoxBlockadeCollision() { ++boxBlockadeCollisionCount; }
  public void invokedBoxWallCollision() { ++boxWallCollisionCount; }

  // Setter Methods

  public void setName(String name) {
    this.name = name;
  }

  // Getter Methods

  public String getName() {
    return name;
  }

  public Map getMap() { return map; }
  public Player getPlayer() { return player; }

  public int getValidMoves() { return validMoveCount; }
  public int getWrongMoves() { return wrongMoveCount; }
  public int getInvalidKeystrokes() { return invalidKeystrokeCount; }
  public int getOverlapMoves() { return overlapCount; }
  public int getHitWallMoves() { return hitWallCount; }
  public int getBoxBlockadeCollisions() { return boxBlockadeCollisionCount; }
  public int getBoxWallCollisions() { return boxWallCollisionCount; }

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
  public int compareTo(User p) {
    return this.getScore() - p.getScore();
  }
}

class Map {
  private Stack<Entity>[][] fastGrid;
  private static int[][] templateGrid;

  public Map() {
    this.initMap();
  }

  @SuppressWarnings("unchecked")
  private void initMap() {
    int xL = templateGrid.length;
    int yL = templateGrid[0].length;
    
    fastGrid = (Stack<Entity>[][]) new Stack<?>[xL][yL];

    for(int i = 0; i < xL; ++i)
      for(int j = 0; j < yL; ++j)
        fastGrid[i][j] = new Stack<Entity>();

    for(int i = 0; i < xL; ++i) {
      for(int j = 0; j < yL; ++j) {
        switch(templateGrid[i][j]) {
          case 0:
            break;
          case 1:
            fastGrid[i][j].push(new Wall()); break;
          case 2:
            break;
          case 3:
            break;
          case 4:
            fastGrid[i][j].push(new Blockade()); break;
          case 5:
            fastGrid[i][j].push(new Box()); break;
          default:
            // invalid input
            // no need to handle
            break;
        }
      }
    }
  }

  public boolean isEmpty(int x, int y) { return fastGrid[x][y] == null; }
  public boolean isWall(int x, int y) { return fastGrid[x][y].peek() instanceof Wall; }
  public boolean isBlockade(int x, int y) { return fastGrid[x][y].peek() instanceof Blockade; }
  public boolean isBox(int x, int y) { return fastGrid[x][y].peek() instanceof Box; }

  public void placePlayer(Player player, int pX, int pY) {
    fastGrid[pX][pY].push(player);
  }

  public static void setTemplateGrid(ArrayList<ArrayList<Integer>> grid, int maxX, int maxY) {
    templateGrid = new int[maxX][maxY];

    for(int i = 0; i < grid.size(); ++i) {
      int len = grid.get(i).size();
      for(int j = 0; j < len; ++j) { 
        templateGrid[i][j] = grid.get(i).get(j);
      }
    }
  }

  public Stack<Entity> getStack(int x, int y) {
    return fastGrid[x][y];
  }
}

abstract class Entity {
  private boolean canPush = false;    // can be pushed if cannot overlap
  private boolean cOverlap = false;   // character can overlap
  private boolean canOverlap = false; // gloal overlap

  private int x, y;

  public abstract int getType();

  public void setCanPush(boolean b) { this.canPush = b; }
  public void setCanCOverlap(boolean b) { this.cOverlap = b; }
  public void setCanOverlap(boolean b) { this.canOverlap = b; }
  
  public boolean canPush() { return canPush; }
  public boolean canCOverlap() { return cOverlap; }
  public boolean canOverlap() { return canOverlap; }


  public void setPos(int x, int y) { this.x = x; this. y = y; }

  public int[] getPos() { return new int[]{x, y}; }
}

class Player extends Entity {
  private static int type = 2;

  public Player() { }
  public Player(int x, int y) { setPos(x, y); }

  public int getType() { return type; };
}

class Wall extends Entity {
  static int type = 1;

  public Wall() { }

  public int getType() { return type; };
}

class Blockade extends Entity {
  static int type = 4;

  public Blockade() {
    setCanCOverlap(true);
  }

  public int getType() { return type; };
}

class Box extends Entity {
  static int type = 5;

  public Box() {
    setCanPush(true);
  }

  public int getType() { return type; };
}

class Stack<T> {
	@Override
	public String toString() {
		return "Stack [elements=" + elements + "]";
	}

  private List<T> elements;

  public Stack() {
    elements = new ArrayList<>();
  }

  // returns the top of the stack
	public T peek() {
		if (elements.isEmpty()) {
			return null;
		}
		return elements.get(elements.size() - 1);
	}
	
  // pops and returns the top of the stack
	public T pop() {
		if (elements.isEmpty()) {
			return null;
		}
		T top = elements.get(elements.size() - 1);
		elements.remove(elements.size() - 1);
		return top;
	}

  // adds to the top of the stack
	public void push(T element) {
		elements.add(element);
	}

  // get size of stack
	public int size() {
		return elements.size();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
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