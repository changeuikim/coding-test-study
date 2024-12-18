/*
    골드2 - 2662번: 기업투자 https://www.acmicpc.net/problem/2662

    제한 : 1000ms, 128MB
    제약조건 : N 300, M 20

    "투자는 만원 단위"
    "돈을 투자하지 않은 경우"
    "가장 많은 이익을 얻을 수 있는 투자방식"
    "이때의 이익금"
 */

import java.io.*;

class Solution {
    public void dynamic() throws IOException  {
        int N = nextInt(), M = nextInt();

        // 1. 2차원 : 투자 x 이익, 1-based
        int[][] profit = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                profit[i][j] = nextInt();
            }
        }

        // 2. 다이나믹 프로그래밍밍
        // DP[N][M] : 총 투자 N원으로 M번째 기업까지의 최대 이익
        // memo[N][M] : 총 투자 N원으로 M 기업이 받은 투자액
        int[][] dp = new int[N + 1][M + 1];
        int[][] memo = new int[N + 1][M + 1];

        // 루프 : 기업j -> 투자i -> 변동k
        // 점화식 : dp[N][다음 기업] = dp[N-k][이전 기업] + profit[k][다음 기업]
        for (int j = 1; j <= M; j++) {
            for (int i = 0; i <= N; i++) {
                for (int k = 0; k <= i; k++) {
                    int nxt = dp[i - k][j - 1] + profit[k][j];
                    if (dp[i][j] < nxt) {
                        dp[i][j] = nxt;
                        memo[i][j] = k;
                    }
                }
            }
        }
        // 투자 N으로 기업 M까지의 최대 이익
        System.out.println(dp[N][M]);

        // 3. 기업별 투자액 역추적
        int[] trace = new int[M + 1];
        int invest = N;

        for (int corp = M; corp > 0; corp--) {
            trace[corp] = memo[invest][corp];
            invest -= trace[corp];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= M; i++) {
            sb.append(trace[i]).append(" ");
        }
        
        System.out.println(sb);
    }

    int nextInt() throws IOException {
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
    public static void main(String[] args) throws IOException  {
        new Solution().dynamic();
    }
}