package com.typicalbot.console;

import java.io.DataInputStream;
import java.io.IOException;

public class ConsoleReader {
    private final int BUFFER_SIZE = 1 << 16;

    private DataInputStream stream;
    private byte[] buffer;
    private int bufferPoint;
    private int bytesRead;

    public ConsoleReader() {
        this.stream = new DataInputStream(System.in);
        this.buffer = new byte[BUFFER_SIZE];
        this.bufferPoint = 0;
        this.bytesRead = 0;
    }

    public String readLine() throws IOException {
        byte[] buf = new byte[64];
        int cnt = 0;
        int c;

        while ((c = read()) != -1) {
            if (c == '\n') {
                break;
            }

            buf[cnt++] = (byte) c;
        }

        return new String(buf, 0, cnt);
    }

    public int nextInt() throws IOException {
        int result = 0;
        byte c = read();

        while (c <= ' ') {
            c = read();
        }

        boolean negative = (c == '-');

        if (negative) {
            c = read();
        }

        do {
            result = result * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        return (negative) ? -result : result;
    }

    public long nextLong() throws IOException {
        long result = 0;
        byte c = read();

        while (c <= ' ') {
            c = read();
        }

        boolean negative = ((c == '-'));

        if (negative) {
            c = read();
        }

        do {
            result = result * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        return (negative) ? -result : result;
    }

    public double nextDouble() throws IOException {
        double result = 0;
        double divider = 0;
        byte c = read();

        while (c <= ' ') {
            c = read();
        }

        boolean negative = (c == '-');

        if (negative) {
            c = read();
        }

        do {
            result = result * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (c == '.') {
            while ((c = read()) >= '0' && c <= '9') {
                result += (c - '0') / (divider *= 10);
            }
        }

        return (negative) ? -result : result;
    }

    private void fill() throws IOException {
        bytesRead = stream.read(buffer, bufferPoint = 0, BUFFER_SIZE);

        if (bytesRead == -1) {
            buffer[0] = -1;
        }
    }

    private byte read() throws IOException {
        if (bufferPoint == bytesRead) {
            fill();
        }

        return buffer[bufferPoint++];
    }

    public void close() throws IOException {
        if (stream == null) return;
        stream.close();
    }
}
