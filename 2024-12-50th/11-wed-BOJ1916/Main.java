/*
    골드5 - 1916번: 최소비용 구하기 https://www.acmicpc.net/problem/1916

    제한 : 500ms, 128MB
    제약조건 : N 10^3, M 10^5, 비용 10^5, 가능한 경우만

    "출발 도시에서 도착 도시까지 가는데 드는 최소 비용" + 음수 가중치 없음 -> 다익스트라
 */

import java.io.*;
import java.util.*;

// 다음노드 v, 가중치 w
class Node {
    int v, weight;
    Node(int v, int weight) {
        this.v = v;
        this.weight = weight;
    }
}

// 이동비용 cost, 현재노드 u
class State {
    int cost, u;
    State(int cost, int u) {
        this.cost = cost;
        this.u = u;
    }
}

class Solution {
    public void dijkstra() throws IOException  {
        int N = nextInt(), M = nextInt();

        // 1. 인접리스트 초기화 : 1-based
        List<List<Node>> graph = new ArrayList<>(N + 1);
        for (int i = 0; i <= N; i++) graph.add(new ArrayList<>());

        while (M-- > 0) {
            int u = nextInt(), v = nextInt(), w = nextInt();
            graph.get(u).add(new Node(v, w));
        }
        
        // 2. 다익스트라 초기화
        // 출발 도시 S, 도착 도시 E
        int S = nextInt(), E = nextInt();

        // 거리 배열 초기화
        int MAX = 100000000;
        int[] dist = new int[N + 1];
        Arrays.fill(dist, MAX);
        dist[S] = 0;

        // 힙 초기화
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        pq.offer(new State(0, S));

        // 다익스트라
        while (!pq.isEmpty()) {
            State cur = pq.poll();

            if (cur.cost > dist[cur.u]) continue;
            if (cur.u == E) break;

            for (Node nxt : graph.get(cur.u)) {
                int nxtCost = cur.cost + nxt.weight;
                if (dist[nxt.v] > nxtCost) {
                    dist[nxt.v] = nxtCost;
                    pq.offer(new State(nxtCost, nxt.v));
                }
            }
        }

        System.out.println(dist[E]);
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
    public static void main(String[] args) throws IOException  {
        new Solution().dijkstra();
    }
}