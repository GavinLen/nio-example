package xyz.ieden.java;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


import java.nio.LongBuffer;

/**
 * @author gavin
 * @date 2018/8/26 8:09
 */
public class BufferTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(BufferTest.class);

    @Test
    public void testOnlyReadBuffer() {

        // 初始化 buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 赋值
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        // 创建只读 buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        // 修改原 buffer 内容
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i, (byte) (buffer.get(i) * 10));
        }

        // 修改其位置
        readOnlyBuffer.position(0);
        // 修改其限制
        readOnlyBuffer.limit(buffer.capacity());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

    }

    @Test
    public void testBufferSlice() {
        // 创建 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 赋值
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        // 设置 buffer 位置
        buffer.position(3);
        // 设置 buffer 限度
        buffer.limit(7);
        // 创建分片
        ByteBuffer slice = buffer.slice();

        // 修改分片的值
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            slice.put(i, (byte) (b * 10));
        }

        // 设置 buffer 位置
        buffer.position(0);
        // 设置 buffer 限度
        buffer.limit(buffer.capacity());

        // 输出 buffer 内容
        while (buffer.hasRemaining()) {
            LOGGER.info("{}.", buffer.get());
        }

    }

    @Test
    public void testProperty() {
        IntBuffer buffer = IntBuffer.allocate(8);

        // 初始化创建
        LOGGER.info("init:");
        outputProperty(buffer);


        // 插入元素
        buffer.put(1);
        buffer.put(2);
        buffer.put(3);
        buffer.put(4);
        LOGGER.info("put:");
        outputProperty(buffer);

        // 翻转后
        buffer.flip();
        LOGGER.info("flip:");
        outputProperty(buffer);

        // 读取元素
        int i = buffer.get();
        LOGGER.info("get");
        outputProperty(buffer);

        Buffer clear = buffer.clear();
        LOGGER.info("clear:");
        outputProperty(buffer);

    }

    private void outputProperty(Buffer buffer) {
        LOGGER.info("Position:{}, Limit:{}, Capacity:{}.", buffer.position(), buffer.limit(), buffer.capacity());
    }

    @Test
    public void testLongBuffer() {
        // 分配 IntBuffer
        LongBuffer buffer = LongBuffer.allocate(8);

        // 插入数据
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(2 * (i + 1));
        }

        // 修改 buffer 状态
        buffer.flip();

        // 读数据
        while (buffer.hasRemaining()) {
            long l = buffer.get();
            LOGGER.info("{}", l);
        }
    }

    @Test
    public void testIntBuffer() {
        // 分配 IntBuffer
        IntBuffer buffer = IntBuffer.allocate(8);

        // 写数据
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(2 * (i + 1));
        }

        // 修改 buffer 状态
        buffer.flip();

        // 读数据
        while (buffer.hasRemaining()) {
            int i = buffer.get();
            LOGGER.info("{}", i);
        }
    }

}
