/*
    골드1 - 15653번: 구슬 탈출 4  https://www.acmicpc.net/problem/15653

    제한 : 2000ms, 512MB
    제약조건 : N 10, M 10

    "보드에는 구멍이 하나"
    "게임의 목표는 빨간 구슬"
    "파란 구슬이 구멍에 들어가면 안 된다."
    "중력을 이용해서 이리 저리"
    "빨간 구슬이 구멍에 빠지면 성공이지만, 파란 구슬이 구멍에 빠지면 실패이다."
    "동시에 같은 칸에 있을 수 없다."
    "기울이는 동작을 그만하는 것은 더 이상 구슬이 움직이지 않을 때 까지이다."
 */

import java.io.*;
import java.util.*;

class Orb {
    int y, x, count;

    Orb(int y, int x) {
        this.y = y;
        this.x = x;
    }

    Orb(int y, int x, int count) {
        this.y = y;
        this.x = x;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Orb orb = (Orb) o;
        return this.y == orb.y &&
               this.x == orb.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }
}

class State {
    Orb red, blue;
    int move;

    State(Orb red, Orb blue, int move) {
        this.red = red;
        this.blue = blue;
        this.move = move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        State state = (State) o;
        return this.red.equals(state.red) &&
               this.blue.equals(state.blue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, blue);
    }
}

class Solution {
    private static char[][] board;

    public void bfs() throws IOException  {
        int N = nextInt(), M = nextInt();

        // 2차원 보드, 빨강, 파랑 초기화
        board = new char[N][M];
        Orb red = null, blue = null;

        for (int i = 0; i < N; i++) {
            char[] line = nextLine().toCharArray();

            for (int j = 0; j < M; j++) {
                if (line[j] == 'R') {
                    red = new Orb(i, j);
                    line[j] = '.';
                }
                else if (line[j] == 'B') {
                    blue = new Orb(i, j);
                    line[j] = '.';
                }
            }

            board[i] = line;
        }

        // 방향 설정 : 좌우상하
        int[] dy = {0, 0, 1, -1};
        int[] dx = {-1, 1, 0, 0};

        // 큐 초기화
        Queue<State> q = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        State start = new State(red, blue, 0);
        q.offer(start);
        visited.add(start);

        // BFS
        while (!q.isEmpty()) {
            State cur = q.poll();

            Orb cr = cur.red;
            Orb cb = cur.blue;
            int move = cur.move;

            // 방향전환
            for (int i = 0; i < 4; i++) {
                Orb nr = roll(cr.y, cr.x, dy[i], dx[i]);
                Orb nb = roll(cb.y, cb.x, dy[i], dx[i]);

                // 파란 구슬이 구멍에 들어가면 안 된다
                if (board[nb.y][nb.x] == 'O') continue;

                // 게임의 목표는 빨간 구슬
                if (board[nr.y][nr.x] == 'O') {
                    System.out.println(move + 1);
                    return;
                }
                // 동시에 같은 칸에 있을 수 없다
                if (nr.y == nb.y && nr.x == nb.x) {
                    // 더 많이 움직인 것을 뒤로
                    if (nr.count > nb.count) {
                        nr.y -= dy[i];
                        nr.x -= dx[i];
                    }
                    else {
                        nb.y -= dy[i];
                        nb.x -= dx[i];
                    }
                }

                // 큐에 추가
                State nxt = new State(nr, nb, move + 1);
                if (visited.contains(nxt)) continue;

                q.offer(nxt);
                visited.add(nxt);
            }
        }

        System.out.println(-1);
    }

    private static Orb roll(int y, int x, int dy, int dx) {
        int count = 0;
        // 다음이 벽이 아니고, 지금이 구멍이 아니라면
        while (board[y + dy][x + dx] != '#' && board[y][x] != 'O') {
            y += dy;
            x += dx;
            count++;
        }
        return new Orb(y, x, count);
    }

    private static int nextInt() throws IOException {
        int n = 0;
        int c;
        while ((c = System.in.read()) <= 32);
        do {
            n = n * 10 + (c - '0');
        } while ((c = System.in.read()) > 32);
        return n;
    }

    private static String nextLine() throws IOException {
        char[] buf = new char[10];
        int c, idx = 0;
        while ((c = System.in.read()) <= 32);
        do {
            buf[idx++] = (char)c;
        } while((c = System.in.read()) > 10);
        return new String(buf, 0, idx);
    }
}

public class Main {
    public static void main(String[] args) throws IOException  {
        new Solution().bfs();
    }
}