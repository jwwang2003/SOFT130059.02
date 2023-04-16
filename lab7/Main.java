import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class Main {
  public static void main(String[] args) throws IOException {
    Game g = new Game();
    g.init();

    while(g.run()) {

    }

    Knight k = (Knight)g.characterList.get("Knight");
    Witch w = (Witch)g.characterList.get("Witch");
    Archer a = (Archer)g.characterList.get("Archer");

    System.out.println("Knight " + k.x + " " + k.y + " " + k.health);
    System.out.println("Witch " + w.x + " " + w.y + " " + w.health);
    System.out.println("Archer " + a.x + " " + a.y + " " + a.health);

    return;
  }
}

// Game driver code
class Game {
  static Reader reader;
  private int _mapX, _mapY; // stores map size
  HashMap<String, Player> characterList;

  Player cMap[][];

  public Game() {
    reader = new Reader();
    characterList = new HashMap<>();
  }

  public void init() {
    readMapSize();
    // initialize charMap
    cMap = new Player[_mapX][_mapY];
    readCharacterPos();
  }

  public void readMapSize() {
    try {
      _mapX = reader.nextInt();
      _mapY = reader.nextInt();
    } catch (IOException e) {
      System.out.println("Error reading map size : " + e);
      readMapSize();
    }
  }

  public void readCharacterPos() {
    try {
      for(int i = 0; i < 3; ++i) {
        String temp[] = reader.readLine().split(" ");
        int pX = Integer.parseInt(temp[1]);
        int pY = Integer.parseInt(temp[2]);

        Player c;
        switch(temp[0]) {
          case "Knight":
            c = new Knight(cMap, pX, pY);
            break;
          case "Witch":
            c = new Witch(cMap, pX, pY);
            break;
          case "Archer":
            c = new Archer(cMap, pX, pY);
            break;
          default:
            c = null;
            break;
        }

        cMap[pX][pY] = c;
        characterList.put(temp[0], c);
      }
    } catch (IOException e) {
      System.out.println("Error reading in character data: " + e);
      readCharacterPos();
    }
  }

  public boolean run() throws IOException {
    String buffer[] = reader.readLine().split(" ");

    if(buffer[0].equals("End")) return false;
    
    if(Character.isDigit(buffer[1].charAt(0))) {
      // action
      Player p = characterList.get(buffer[0]);

      switch(buffer[1]) {
        case "1":
          p.attack(Integer.parseInt(buffer[2]), Integer.parseInt(buffer[3]));
          break;
        case "2":
          p.specialAttack(Integer.parseInt(buffer[2]), Integer.parseInt(buffer[3]));
          break;
        case "3":
          if(p instanceof RangedAttack) {
            if(buffer[0].equals("Witch")) {
              Witch w = (Witch) p;
              w.rangedAttack(Integer.parseInt(buffer[2]), Integer.parseInt(buffer[3]));
            } else {
              Archer a = (Archer) p;
              a.rangedAttack(Integer.parseInt(buffer[2]), Integer.parseInt(buffer[3]));
            }
          }
          break;
        case "4":
          if(buffer[0].equals("Witch")) {
            Witch w = (Witch) p;
            w.health += 30;
            if(w.health > 40) w.health = 40;
          } else {
            Knight k = (Knight) p;
            k.health += 40;
          }
          break;
        case "5":
          Knight k = (Knight) p;
          k.health += 50;
          break;
      }
    } else {
      // movement
      switch (buffer[1]) {
        case "h":
        case "j":
        case "k":
        case "l":
          int m = Player.Move.valueOf(buffer[1]).ordinal();
          characterList.get(buffer[0]).move(m);
          break;
        default:
          break;
      }
    }

    return true;
  }
}

abstract class Player {
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

  protected int health;
  protected int strength;
  protected int x, y;
  protected int stepDistance;

  protected Player charMap[][];

  // Abstract methods that subclasses will implement
  public abstract void takeDamage(int damage);
  public abstract void specialAttack(int x, int y);

  public void setMap(Player map[][]) {
    this.charMap = map;
  }

  // Regular attack towards a character
  public void attack(int x ,int y) {
    Player target = charMap[x][y];
    target.takeDamage(strength);
  }

  // Basic getter and setter methods
  public int getHealth() {
    return this.health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getStrength() {
    return this.strength;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public int[] getPos() {
    return new int[]{x, y};
  }

  public void setPos(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void move(int direction) {
    int d[] = Player.d[direction];
    int dX = d[0] * stepDistance;
    int dY =  d[1] * stepDistance;

    Player temp = charMap[this.x][this.y];
    charMap[this.x + dX][this.y + dY] = temp;
    charMap[this.x][this.y] = null;

    temp.x = this.x + dX;
    temp.y = this.y + dY;
  }

  public Player[] getPosSurround(int x, int y) {
    Player ans[] = new Player[d.length + 1];

    for(int i = 0; i < d.length; ++i) {
      ans[i] = charMap[x + d[i][0]][y + d[i][1]];
    }

    ans[4] = charMap[x][y];

    return ans;
  }
}

interface RangedAttack {
  void rangedAttack(int x, int y);
}

class Knight extends Player {
  private int armor;

  public Knight(Player[][] cMap, int x, int y) {
    this.charMap = cMap;
    this.setPos(x, y);
    this.health = 100;
    this.strength = 30;
    this.armor = 5;
    super.stepDistance = 1;
  }

  public void takeDamage(int damage) {
    // code for the knight's damage
    this.health -= (damage - armor);
    if(this.health < 0) this.health = 0;
  }

  public void specialAttack(int x, int y) {
    Player characters[] = super.getPosSurround(this.x, this.y);
    for(Player character : characters) {
      if(character == null) continue;
      if(character == this) continue;
      character.takeDamage(40);
    }
    this.health -= 20;
  }
}

class Witch extends Player implements RangedAttack {
  public Witch(Player[][] cMap, int x, int y) {
    this.charMap = cMap;
    this.setPos(x, y);
    this.health = 40;
    this.strength = 10;
    this.stepDistance = 1;
  }

  public void takeDamage(int damage) {
    // code for the knight's damage
    this.health -= (damage);
    if(this.health < 0) this.health = 0;
  }

  public void rangedAttack(int x, int y) {
    Player characters[] = super.getPosSurround(x, y);
    for(Player character : characters) {
      if(character == null) continue;
      character.takeDamage(30);
    }
  }

  public void specialAttack(int x, int y) {
    Player target = charMap[x][y];
    if(target == null) return;
    target.takeDamage(50);
    this.health += 20;
  }
}

class Archer extends Player implements RangedAttack {
  public Archer(Player[][] cMap, int x, int y) {
    this.charMap = cMap;
    this.setPos(x, y);
    this.health = 60;
    this.strength = 20;
    this.stepDistance = 2;
  }

  public void takeDamage(int damage) {
    // code for the knight's damage
    this.health -= (damage * 1.5);
    if(this.health < 0) this.health = 0;
  }

  public void rangedAttack(int x, int y) {
    Player characters[] = super.getPosSurround(x, y);
    for(Player character : characters) {
      if(character == null) continue;
      character.takeDamage(20);
    }
  }

  public void specialAttack(int x, int y) {
    Player target = charMap[x][y];
    if(target == null) return;
    target.takeDamage(20);
  }
}

// Custom fast input reader class
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