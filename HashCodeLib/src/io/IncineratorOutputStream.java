package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class IncineratorOutputStream extends PrintStream {

    public IncineratorOutputStream() {
        super(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // do nothing
            }

            @Override
            public void write(byte[] b) throws IOException {
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
            }
        });
    }
}
