/*
 * Copyright 2019 Bryan Pikaard, Nicholas Sylke and the TypicalBot contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.util.console;

import java.io.DataInputStream;
import java.io.IOException;

public class ConsoleReader {
    /**
     * The maximum buffer size.
     */
    private static final int BUFFER_SIZE = 1 << 16;

    /**
     * The stream to read the user input.
     */
    private DataInputStream stream;

    /**
     * The buffer.
     */
    private byte[] buffer;

    /**
     * The point at which the buffer is currently reading at.
     */
    private int bufferPoint;

    /**
     * The amount of bytes the buffer has read.
     */
    private int bytesRead;

    /**
     * Constructor.
     */
    public ConsoleReader() {
        this.stream = new DataInputStream(System.in);
        this.buffer = new byte[BUFFER_SIZE];
        this.bufferPoint = 0;
        this.bytesRead = 0;
    }

    /**
     * Read the next line of the console.
     *
     * @return string
     * @throws IOException unable to read stream
     */
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

    /**
     * Get the next int of the console.
     *
     * @return int
     * @throws IOException unable to read stream
     */
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

    /**
     * Get the next long of the console.
     *
     * @return long
     * @throws IOException unable to read stream
     */
    public long nextLong() throws IOException {
        long result = 0;
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

    /**
     * Get the next double of the console.
     *
     * @return double
     * @throws IOException unable to read stream
     */
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

    /**
     * Fill the buffer from the stream.
     *
     * @throws IOException unable to read stream
     */
    private void fill() throws IOException {
        bytesRead = stream.read(buffer, bufferPoint = 0, BUFFER_SIZE);

        if (bytesRead == -1) {
            buffer[0] = -1;
        }
    }

    /**
     * Read the stream.
     *
     * @return byte buffer
     * @throws IOException unable to read stream
     */
    private byte read() throws IOException {
        if (bufferPoint == bytesRead) {
            fill();
        }

        return buffer[bufferPoint++];
    }

    /**
     * Close the stream.
     *
     * @throws IOException unable to close stream
     */
    public void close() throws IOException {
        if (stream == null) return;
        stream.close();
    }
}
