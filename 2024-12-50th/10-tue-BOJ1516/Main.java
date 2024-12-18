/*
    골드3 - 1516번: 게임 개발 https://www.acmicpc.net/problem/1516

    제한 : 2000ms, 128MB
    제약조건 : N 500, item 10^5, 가능한 입력만

    "다른 건물을 먼저 지어야" -> 위상 정렬
    "각 건물이 완성되기까지 걸리는 최소 시간" -> DP : 초기시간 + max
 */

import java.io.*;
import java.util.*;

class Solution {
    public void topologicalSort() throws IOException  {
        // 1. 인접리스트, 진입차수, 건물시간 초기화
        int N = nextInt();

        List<List<Integer>> graph = new ArrayList<>(N + 1);
        int[] inDegree = new int[N + 1];
        int[] buildTime = new int[N + 1];

        // 1-based : N + 1
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // 인접리스트 : u -> v
        for (int v = 1; v <= N; v++) {
            buildTime[v] = nextInt();

            while (true) {
                int u = nextInt();
                if (u == -1) break;

                graph.get(u).add(v);
                inDegree[v]++;                
            }
        }

        // 2. 큐, DP 초기화 : 진입차수 0
        Queue<Integer> q = new LinkedList<>();
        int[] dp = new int[N + 1];
        
        for (int v = 1; v <= N; v++) {
            if (inDegree[v] == 0) {
                q.offer(v);
                dp[v] = buildTime[v];
            }
        }

        // 3. 위상 정렬
        while (!q.isEmpty()) {
            int u = q.poll();

            for (int v : graph.get(u)) {
                inDegree[v]--;
                dp[v] = Math.max(dp[v], dp[u] + buildTime[v]);
                if (inDegree[v] == 0) {
                    q.offer(v);
                }
            }
        }

        // 4. 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(dp[i]).append("\n");
        }

        System.out.println(sb);
    }

    private static int nextInt() throws IOException {
        int n = 0;
        int c;
        boolean neg = false;
        while ((c = System.in.read()) <= 32);
        if (c == '-') {
            neg = true;
            c = System.in.read();
        }
        do {
            n = n * 10 + (c - '0');
        } while ((c = System.in.read()) > 32);
        return neg ? -n : n;
    }
}

public class Main {
    public static void main(String[] args) throws IOException  {
        new Solution().topologicalSort();
    }
}