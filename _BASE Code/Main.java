/*
    백준 골드 랜덤 디펜스 스터디
    - nextInt : C++처럼 공백 단위로 숫자를 반환하는 메서드
    - nextLine : br.readLine처럼 개행 단위로 문자열을 반환하는 메서드
    - StringBuilder : 문자 시퀀스를 조작하기 위한 클래스
 */

import java.io.*;

class Solution {
    public void solution() throws IOException  {
        int N = nextInt();
        String line = nextLine();

        StringBuilder sb = new StringBuilder();
        sb.append(N).append("\n");
        sb.append(line);
        System.out.println(sb);
    }

    int nextInt() throws IOException {
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

    String nextLine() throws IOException {
        char[] buf = new char[1024];
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
        new Solution().solution();
    }
}