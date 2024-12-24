/*
    골드3 - 10723번: 판게아 1  https://www.acmicpc.net/problem/10723

    제한 : 20000ms, 256MB
    제약조건 : V = 1e5, E = 1e5, W = 1e7

    간선을 지속적으로 추가했을때의 최단거리 가중치 합을 구하고, 그것들을 XOR 연산하라는 문제이다.
    간선과 관련되어 있기 때문에 최소 신장 트리를 구하는 알고리즘 중 크루스칼을 생각했다.
    간선이 추가될때마다 크루스칼을 반복하면 되는 문제
    
    Fail 01 : 그때마다 간선리스트의 정렬이 실행되기 때문에 간선리스트를 담을 자료구조를 처음엔 우선순위큐로 정하였다.
    하지만 우선순위 큐는 내부 요소를 pop하면서 정렬을 확인할 수 있기 때문에 리스트의 복사가 있었고, 파이썬은 통과는 했지만 	215528 KB	57820 ms라는 말도 안되는 시간복잡도. 사실상의 실패

    Fail 02 : 파이썬에 있는 bisect의 insort를 사용해서 	127144 kb	3596 ms까지 줄어들었다. TypeScript는 라이브러리가 없기 때문에 insort 메서드를 직접 구현해서 로컬에서는 성공했다. 하지만 백준에서는 25% 시점에서 실패했다. 이때까지는 이유를 알지 못했다 -> BigInt가 필요한 문제였다

    Fail 03 : 자바와 C++의 TreeSet과 Set을 사용해서 bisect와 같은 효과를 기대했는데, 로컬에서는 되는데 백준에서는 실패했다. Edge 클래스를 정의도 해보고, new int[]{}도 써봤는데 모두 실패. 이 시점에서 mstCost의 중간 결과가 int 범위를 초과할 수 있다는 것을 알았다. XOR를 봤을때 W의 1e7 크기만 보고 int 이내일거라 생각했는데 아니었다.

    Success 01 : 서로소 집합의 파인드 함수 경로압축은 파이썬, 타입스크립트는 재귀가 빠르고, 자바, C++는 반복문이 빠른 것으로 나타났다. 부모 노드는 1 ~ N도 써보고 음수 랭크도 써봤지만, 랭크 기반의 트리 압축에 따른 시간적 이득은 없는 것으로 나타났다.
 */

import java.io.*;
import java.util.*;

class Solution {
    // 전역변수
    private static int N;
    private static int[] parent;

    private static int find(int x) {
        while (x != parent[x]) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    private static boolean union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return false;

        if (a > b) parent[a] = b;
        else parent[b] = a;
        return true;
    }

    private static long kruskal(List<int[]> edges) {
        // 부모 + 랭크 노드
        parent = new int[N + 1];
        Arrays.setAll(parent, i -> i);

        // 가중치 기준 정렬
        edges.sort(Comparator.comparingInt(o -> o[0]));

        // 최소 신장 트리
        long mstCost = 0;
        int edgeCount = 0;

        for (int[] edge : edges) {
            int w = edge[0], u = edge[1], v = edge[2];
            if (edgeCount >= N - 1) break;
            if (union(u, v)) {
                mstCost += w;
                edgeCount++;
            }
        }

        return mstCost;
    }

    public void solution() throws IOException {
        // 1. 테스트 T회
        int T = nextInt();
        while (T-- > 0) {
            N = nextInt();
            int M = nextInt();

            // 2. 태초의 세계, 엣지 리스트
            List<int[]> edges = new ArrayList<>();
            for (int i = 1; i < N; i++) {
                int u = nextInt(), c = nextInt();
                edges.add(new int[]{c, i, u}); // w, u, v
            }

            // 3. 조물주가 새로이 놓은 도로
            long result = 0;
            for (int j = 1; j <= M; j++) {
                int u = nextInt(), v = nextInt(), c = nextInt();
                edges.add(new int[]{c, u, v}); // w, u, v

                // 5. XOR 연산
                result ^= kruskal(edges); // 4. 크루스칼
            }

            System.out.println(result);
        }
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
