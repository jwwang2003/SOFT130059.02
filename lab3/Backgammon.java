import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Collections;

public class Backgammon {
  static Reader in = new Reader();

  public static void main(String[] args) throws IOException {
    int height, width;

    height = in.nextInt();
    width = in.nextInt();

    Board board = new Board(height, width);
    
    while(true) {
      // String[] arr = in.readLine().split(" ");
      // char c = arr[0].toCharArray()[0];
      
      // int x = Integer.parseInt(arr[1]);
      // int y = Integer.parseInt(arr[2]);
      char c = (char)in.read();
      int x = in.nextInt();
      int y = in.nextInt();


      if(board.newPlay(c, x - 1, y - 1)) break;
      // board.show();
    }
  }

  static class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public Reader()
    {
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public Reader(String file_name) throws IOException
    {
        din = new DataInputStream(
            new FileInputStream(file_name));
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public String readLine() throws IOException
    {
        byte[] buf = new byte[64]; // line length
        int cnt = 0, c;
        while ((c = read()) != -1) {
            if (c == '\n') {
                if (cnt != 0) {
                    break;
                }
                else {
                    continue;
                }
            }
            buf[cnt++] = (byte)c;
        }
        return new String(buf, 0, cnt);
    }

    public int nextInt() throws IOException
    {
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

    public long nextLong() throws IOException
    {
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

    public double nextDouble() throws IOException
    {
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

    private void fillBuffer() throws IOException
    {
        bytesRead = din.read(buffer, bufferPointer = 0,
                              BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private byte read() throws IOException
    {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    public void close() throws IOException
    {
        if (din == null)
            return;
        din.close();
    }
  }

  static class Piece implements Comparable<Piece> {
    private char color;
    private int order;
    private int posY, posX;
  
    // default constructor
    public Piece() {
      color = ' ';
      order = 0;
      posY = 0;
      posX = 0;
    }
  
    public Piece(char Color, int Order, int y, int x) {
      color = Color;
      order = Order;
      posY = y;
      posX = x;
    }
  
    public char getColor() {
      return color;
    }
  
    public int getOrder() {
      return order;
    }
  
    public int[] getPos() {
      return new int[]{posY, posX};
    }
  
    public void setColor(char Color) {
      color = Color;
    }
  
    public void setOrder(int Order) {
      order = Order;
    }
  
    @Override
    public int compareTo(Piece p) {
      return this.getOrder() - p.getOrder();
    }
  }
  
  static class Board {
    private Piece[][] board;
    private char prevP;
    private int playCounter;
    private ArrayList<Piece> winning;
  
    public Board(int y, int x) {
      board = new Piece[y][x];
      playCounter = 0;
    }

    public void showWinning() {
      Collections.sort(winning);
      for(Piece p : winning) {
        int []coords = p.getPos();
        System.out.print("(" + (coords[0] + 1) + "," + (coords[1] + 1) + ")");
      }
    }

    public void show() { 
      for(int i = 0; i < board.length; ++i) {
        for (int j = 0; j < board[0].length; ++j) {
          if(board[i][j] != null)
            System.out.print(board[i][j].getColor());
          else System.out.print("-");
        }
        System.out.println();
      }
    }
  
    public boolean newPlay(char c, int y, int x) {
      if(isFull()) {
        System.out.println(4);
        return true;
      }

      switch(placePiece(c, y, x)) {
        case 1:
          System.out.println(1);
          break;
        case 2:
          System.out.println(2);
          break;
        case 3:
          System.out.println(3);
          break;
        case 4:
          prevP = c;
          break;
        case 5:
          showWinning();
          return true;
        default:
          break;
      }

      return false;
    }

    private int placePiece(char c, int y, int x) {
      if(!isLegal(c)) return 3;
      if(!inBounds(y, x)) return 2;
      if(board[y][x] != null) return 1;

      board[y][x] = new Piece(c, playCounter++, y, x);

      ArrayList<Piece> ans = check(c, y, x);
      if(ans.size() == 5) {
        Collections.sort(ans);
        winning = ans;
        return 5;
      }

      return 4;
    }

    public Piece getPieceAtCord(int y, int x) {
      return board[y][x];
    }

    private ArrayList<Piece> check(char p, int y, int x) {
      int [][] D = {
        {-1, 0},
        {-1, 1},
        {0, 1},
        {1, 1},
      };
      
      for(int i = 0; i < D.length; ++i) {
        ArrayList<Piece> ans = helperCheck(p, D[i], y, x);
        if(ans.size() == 5) return ans;
      }
  
      return new ArrayList<>();
    }
  
    private ArrayList<Piece> helperCheck(char p, int D[], int y, int x) {
      ArrayList<Piece> ans = new ArrayList<Piece>();
      
      for(int i = 0; i < 5; ++i) {
        if(y + i*D[0] >= board.length || y + i*D[0] < 0 || x + i*D[1] >= board[0].length || x + i*D[1] < 0) continue;
        Piece t = this.getPieceAtCord(y + i*D[0], x + i*D[1]);
        if(t != null && t.getColor() == p) {
          ans.add(t);
        } else break;
      }
  
      for(int i = 1; i < 5; ++i) {
        if(y - i*D[0] >= board.length || y - i*D[0] < 0 || x - i*D[1] >= board[0].length || x - i*D[1] < 0) continue;
        Piece t = this.getPieceAtCord(y - i*D[0], x - i*D[1]);
        if(t != null && t.getColor() == p) {
          ans.add(t);
        } else break;
      }
  
      return ans;
    }

    private boolean isFull() {
      return playCounter == board.length*board[0].length;
    }
  
    private boolean isLegal(char p) {
      return !(p == prevP);
    }
  
    private boolean inBounds(int y, int x) {
      return !(y >= board.length || y < 0 || x >= board[0].length || x < 0);
    }
  }
}

