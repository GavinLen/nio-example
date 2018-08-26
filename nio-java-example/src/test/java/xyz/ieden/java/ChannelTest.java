package xyz.ieden.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author gavin
 * @date 2018/8/26 9:42
 */
public class ChannelTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelTest.class);

    private long startTime = 0;

    @Before
    public void startRun() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void entRun() {
        LOGGER.info("Run Time:{}.", System.currentTimeMillis() - startTime);
    }

    /**
     * 通过标准 IO copy 文件
     */
    @Test
    public void testCopyFileByIo() {
        try (InputStream inputStream = new FileInputStream("D:\\a.tar.gz")) {
            try (OutputStream outputStream = new FileOutputStream("D:\\b.tar.gz")) {
                byte[] buffer = new byte[1024];

                int byteToRead;
                while ((byteToRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, byteToRead);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过 NIO Copy 文件
     */
    @Test
    public void testCopyFileByNio() {
        try {
            FileInputStream inputStream = new FileInputStream("D:\\a.tar.gz");
            FileOutputStream outputStream = new FileOutputStream("D:\\b.tar.gz");

            FileChannel inputStreamChannel = inputStream.getChannel();
            FileChannel outputStreamChannel = outputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (inputStreamChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outputStreamChannel.write(byteBuffer);
                byteBuffer.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
