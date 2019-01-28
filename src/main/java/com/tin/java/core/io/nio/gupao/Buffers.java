package com.tin.java.core.io.nio.gupao;

import java.nio.ByteBuffer;

/**
 * Created by Tin on 2018/11/24.
 */
public class Buffers {

    private ByteBuffer readBuffer;

    private ByteBuffer writeBuffer;

    public Buffers(int readCapacity, int writeCapacity) {
        this.readBuffer = ByteBuffer.allocate(readCapacity);
        this.writeBuffer = ByteBuffer.allocate(writeCapacity);
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }
}
