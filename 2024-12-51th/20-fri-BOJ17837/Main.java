/*
    골드2 - 17837번: 새로운 게임 2  https://www.acmicpc.net/problem/17837

    제한 : 500ms, 512MB
    제약조건 : N = 12, M = 10

    체스판이 작으니 파이썬에선 2차원 board가 [0, []]를 요소로 갖는 3차원 배열을 구현했다
    파이썬이나 타입스크립트와 달리 자바는 배열 slice가 곤란하기에 불가피하게 Cell 객체로 board를 구현
    까다로운 스택 작업이 있었다면 LinkedList의 Deque을 고려했겠지만, 슬라이스를 위해 List를 사용했다

    1 ~ 2 페이지 안에 128ms로 구현이 보이기 때문에 138ms인 지금 코드에서 최적화가 가능할 것으로 예상
 */

import java.io.*;
import java.util.*;

class Horse {
    int y, x, d;

    Horse(int y, int x, int d) {
        this.y = y;
        this.x = x;
        this.d = d;
    }
}

class Cell {
    int color = 2;
    List<Integer> stk = new ArrayList<>();

    Cell() {}
}

class Solution {
    public void solution() throws IOException {
        int N = nextInt(), K = nextInt();

        // 방향 : →, ←, ↑, ↓
        int[] dy = {0, 0, 0, -1, 1};
        int[] dx = {0, 1, -1, 0, 0};

        // 뒤집기
        Map<Integer, Integer> rMap = Map.of(
            1, 2,
            2, 1,
            3, 4,
            4, 3
        );

        // 체스판 : 둘레 패딩, 1-based
        // 스택판 : 체스판과 동기화, 스택 크기 4 => 게임 종료
        Cell[][] board = new Cell[N + 2][N + 2];
        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                board[i][j] = new Cell();
            }
        }
        
        // 체스판 초기화
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                board[i][j].color = nextInt();
            }
        }
        
        // 엔트리 : 1번마 ~ K번마
        Horse[] horseList = new Horse[K];
        for (int i = 0; i < K; i++) {
            int y = nextInt(), x = nextInt(), d = nextInt();

            board[y][x].stk.add(i);
            horseList[i] = new Horse(y, x, d);
        }

        // 게임 1000턴
        for (int turn = 1; turn <= 1000; turn++) {
            for (int hIdx = 0; hIdx < K; hIdx++) {
                Horse horse = horseList[hIdx];
                int y = horse.y, x = horse.x, d = horse.d;

                // 이동방향 설정
                int ny = y + dy[d];
                int nx = x + dx[d];

                // 파랑칸 : 이동방향 뒤집기
                if (board[ny][nx].color == 2) {
                    // 뒤집기
                    d = rMap.get(d);
                    horse.d = d;
                    // 이동방향 설정
                    ny = y + dy[d];
                    nx = x + dx[d];
                    // 또 파란벽이면 이동불가
                    if (board[ny][nx].color == 2) continue;
                }

                // 이동 전처리: 스택 분할
                List<Integer> cur = board[y][x].stk; // 현재말 스택
                int slicePtr = cur.indexOf(hIdx); // 현재말 위치
                List<Integer> nxt = new ArrayList<>(cur.subList(slicePtr, cur.size())); // 현재말 위쪽
                board[y][x].stk = new ArrayList<>(cur.subList(0, slicePtr)); // 현재말 아래쪽

                // 빨강색: 스택 뒤집기
                if (board[ny][nx].color == 1) {
                    Collections.reverse((List<?>) nxt);
                }

                // 이동 처리
                board[ny][nx].stk.addAll(nxt);
                for (int idx : nxt) {
                    horseList[idx].y = ny;
                    horseList[idx].x = nx;
                }

               // 스택 크기 검증
                if (board[ny][nx].stk.size() >= 4) {
                    System.out.println(turn);
                    return;
                }
            }
        }

        System.out.println(-1);
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
}

public class Main {
    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }
}
