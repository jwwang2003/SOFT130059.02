package lab5;

public class Main {
  
  
}



class Player {
  private String name;
  private int x, y;

  private int validMovesC;
  private int errorMovesC;
  private int invalidKeystrokesC;
  private int overlapC;
  private int hitWallC;
  
  private boolean won;
  private boolean quit;

  public Player(String name, int x, int y) {
    this.name = name;
    this.x = x;
    this.y = y;

    validMovesC = 0;
    errorMovesC = 0;
    invalidKeystrokesC = 0;
    overlapC = 0;
    hitWallC = 0;

    won = false;
    quit = false;
  }
  
  public boolean win() {
    
  }

  public boolean forfeit() {
    if(quit) return false;

    return quit = !quit;
  }

  public boolean canPlay() {
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

  public void setPos(int x, int y) {
    int dX = Math.abs(this.x - x);
    int dY = Math.abs(this.y - y);

    assert(dX <= 1);
    assert(dY <= 1);

    this.x = x;
    this.y = y;

    moveInvoked();

    return;
  }

  // Getter Methods

  public String getName() {
    return name;
  }

  public int[] getPos() {
    int[] coord = { x, y };
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
}