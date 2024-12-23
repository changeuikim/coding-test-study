/*
    골드1 - 2307번: 도로검문  https://www.acmicpc.net/problem/2307

    제한 : 1000ms, 128MB
    제약조건 : V = 1000, E = 5000, W = 10000

    INF는 V * W보다 큰 1e9로 설정

    Fail 01 : 간선을 한개씩 뺀 인접리스트 5000개에 대한 다익스트라 -> O(E * (E log V))로 시간초과
    Fail 02 : 간선을 한개씩 뺀 간선리스트 5000개에 대한 다익스트라 -> O(E * (E log V))로 시간초과

    지연시간은 최단시간의 경로 중 한 간선을 끊었을때만 발생 -> 기본 다익스트라, 끊긴 다익스트라 2종류를 돌려야한다
    간선을 끊는 시점은? -> for v in graph[u]에서 u -> v가 s -> e와 일치할 때, pq에 넣을지 계산하기 이전
    간선을 저장하는 시점은? -> for v in graph[u]에서 u -> v가 s -> e와 일치할 때, pq에 넣을지 계산된 이후

    Fail 03 : pq의 push, pop 전후의 u, v를 튜플로 저장 -> 4000ms라는 말도 안되는 시간으로 통과. u -> v는 여러번 등장하기 때문

    trace 배열에 대해 u -> v를 인덱스와 값에 할당하는 방식으로 중복을 처리
 */

import java.io.*;
import java.util.*;

class Solution {
    private static final int INF = (int)1e9;
    private static int N;
    private static List<List<int[]>> graph;

    private static class  State {
        int distance;
        int[] trace;

        State(int distance, int[] trace) {
            this.distance = distance;
            this.trace = trace;
        }
    }

    private static State dijkstra(int[] skip, boolean isTrace) {
        // 간선정보 추적
        int[] trace = isTrace ? new int[N + 1] : null;

        // 거리 초기화
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);
        dist[1] = 0;

        // 우선순위 큐 초기화
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 1});

        // 다익스트라
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int cur = current[0], u = current[1];

            if (cur > dist[u]) continue;

            for (int[] edge: graph.get(u)) {
                int v = edge[0], w = edge[1];

                // 간선 끊기
                if (skip[0] == u && skip[1] == v) continue;

                int nxt = cur + w;
                if (nxt < dist[v]) {
                    // 간선 저장
                    if (isTrace) trace[v] = u;
                    dist[v] = nxt;
                    pq.offer(new int[]{nxt, v});
                }
            }
        }
        
        return new State(dist[N], trace);
    }

    public void solution() throws IOException {
        N = nextInt();
        int M = nextInt();

        // 인접리스트 초기화
        graph = new ArrayList<>(N + 1);
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            int u = nextInt(), v = nextInt(), w = nextInt();
            graph.get(u).add(new int[]{v, w});
            graph.get(v).add(new int[]{u, w});
        }

        // 최단 시간과 간선 추적
        State minState = dijkstra(new int[]{0, 0}, true);
        int minTime = minState.distance;
        int[] trace = minState.trace;

        // 최단 시간의 간선 복원
        List<int[]> edges = new ArrayList<>();
        int node = N;
        while (trace[node] != 0) {
            edges.add(new int[]{trace[node], node});
            node = trace[node];
        }

        // 간선 하나씩 끊기
        int maxDiff = -1;
        for (int[] skip : edges) {
            State delayState = dijkstra(skip, false);
            int delayTime = delayState.distance;

            if (delayTime == INF) {
                System.out.println(-1);
                return;
            }

            maxDiff = Math.max(maxDiff, delayTime - minTime);
        }

        System.out.println(maxDiff);
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
