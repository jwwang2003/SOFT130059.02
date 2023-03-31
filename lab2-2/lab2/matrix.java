package lab2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class matrix{
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
    
    static Reader in = new Reader();

    public static void main(String[] args) throws IOException {
        //Write Your Code Here

        int c, t, s;
        // c => number of tests
        // t => type of operation
        // s => size of squared matrix

        c = in.nextInt();

        for(int i = 0; i < c; ++i) {
            t = in.nextInt();
            s = in.nextInt();

            int[][] A = new int[s][s];

            switch(t) {
                case 1:
                case 2:
                    readInput(A);
                    int[][] B = new int[s][s];
                    readInput(B);
                    
                    if(t == 1) addition(A, B);
                    else if(t == 2) dotProduct(A, B);

                    break;
                case 3:
                    readInput(A);
                    transpose(A);
                    break;
            }
        }
    }

    public static void transpose(int[][] matrix) {
        for(int i = 0; i < matrix.length; ++i) {
            for(int j = 0; j < matrix[i].length; ++j) {
                System.out.print(matrix[j][i] + " ");
            }
        }
        System.out.println();
    }

    public static void addition(int[][]A, int[][]B) {
        for(int i = 0; i < A.length; ++i) {
            for(int j = 0; j < A[0].length; ++j)
                System.out.print((A[i][j] + B[i][j]) + " ");
        }
        System.out.println();
    }

    public static void dotProduct(int[][] A, int[][] B) {
        int[][] t = new int[A.length][A[0].length];
        
        for(int i = 0; i < A.length; ++i) {
            // for(int c = 0; c < B.length; ++c) {
            //     for(int j = 0; j < A.length; ++j)
            //         t[i][j] += A[i][c]*B[c][j];
            // }

            for(int j = 0; j < B.length; ++j) {
                for(int c = 0; c < A.length; ++c)
                    t[i][j] += A[i][c]*B[c][j];
            }
        }

        for(int i = 0; i < t.length; ++i)
            for(int j = 0; j < t[0].length; ++j)
                System.out.print(t[i][j] + " ");
        System.out.println();
    }

    public static void readInput(int[][] matrix) throws IOException {
        for(int i = 0; i < matrix.length; ++i) {
            for(int j = 0; j < matrix.length; ++j) {
                matrix[i][j] = in.nextInt();
            }
        }
    }
}