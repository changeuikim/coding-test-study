/*
    골드5 - 3020번: 개똥벌레  https://www.acmicpc.net/problem/3020

    제한 : 1000ms, 128MB
    제약조건 : N = 2 * 10^5, M = 5 * 10^5

    "첫 번째 장애물은 항상 석순"
    "종유석과 석순이 번갈아가면서 등장"
    "일직선으로 지나가면서 만나는 모든 장애물을 파괴"

    10^11은 10^8을 상회하므로 O(N^2)를 쓸 수 없다 -> 2차원 배열의 각 행을 더할 수 없다
    N을 주회하면서 각 위치를 1차원 배열에 기록하는건 가능
    
    1회차는 석순과 종유석 배열을 따로 계산 후 합산
    2회차는 차분(difference)를 활용해 배열 1개로 계산
 */
import java.io.*;

class Solution {
    public void difference() throws IOException {
        int N = nextInt(), H = nextInt();
        int[] height = new int[H];

        // 너비 압축
        for (int i = 0; i < N; i++) {
            int t = nextInt();

            // 짝수 : 석순, 홀수 : 종유석
            if (i % 2 == 0) {
                // 석순 : H-t 이전은 0 이후는 1
                height[H - t]++;
            } else {
                // 종유석 : t 이전은 1 이후는 0
                height[0]++;
                height[t]--;
            }
        }

        // 누적 합
        for (int i = 1; i < H; i++) {
            height[i] += height[i - 1];
        }

        // 최소값 및 빈도
        int minVal = height[0], minCnt = 1;
        for(int i = 1; i < height.length; i++) {
            if(height[i] < minVal) {
                minVal = height[i];
                minCnt = 1;
            } else if(height[i] == minVal) {
                minCnt++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(minVal).append(" ").append(minCnt);

        System.out.println(sb);
    }

    private static int nextInt() throws IOException {
        int n = 0, c;
        while ((c = System.in.read()) <= 32);
        do {
            n = n * 10 + (c - '0');
        } while ((c = System.in.read()) > 32);
        return n;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        new Solution().difference();
    }
}