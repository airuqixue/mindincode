package com.mindincode.reactor.client;


import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by yinsx on 2016/12/13.
 */
public class ReactorClient {
    private int maxRead;
    private int maxWrite;

    private ByteBuffer readBuffer;
    private ByteBuffer writeBuffer;

    SocketChannel socket;

    public ReactorClient() {

    }

    public void connect(String ip, int port) throws IOException {
        socket = SocketChannel.open();
        socket.configureBlocking(false);
        socket.connect(new InetSocketAddress(ip, port));
        int count = 1;
        while (!socket.finishConnect()) {
            if (count == 3) {
                break;
            }
            System.out.println("connect to " + ip);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new InterruptedIOException(e.toString());
            }
            count++;

        }
        if (socket.isConnected()) {
            System.out.println("Successfully connect to " + ip);
        } else {
            System.out.println("Failed connect to " + ip);
        }
    }

    public void read() throws IOException {
        readBuffer = ByteBuffer.allocate(getMaxRead());
        byte[] bytes = new byte[getMaxRead()];
        StringBuffer buf = new StringBuffer();
        if (!socket.isConnected()) {
            System.out.println("not connected");
            return;
        }
        int retRead = socket.read(readBuffer);
        for (; ; ) {

            do {
                if (retRead > 0) {
                    readBuffer.flip();
                    int i = 0;
                    while (readBuffer.hasRemaining()) {
                        bytes[i] = readBuffer.get();
                        i++;
                    }

                    buf.append(new String(bytes));
                    readBuffer.clear();
                }

                retRead = socket.read(readBuffer);
            } while (retRead != 0);
            System.out.println("Read: " + buf.toString());
        }


    }

    public void write(String message) throws IOException {
        if (message == null || "".equals(message)) {
            return;
        }
        if (message.length() > getMaxWrite()) {
            System.out.println("messages are too long than " + getMaxWrite());
            return;
        }
        writeBuffer = ByteBuffer.allocate(message.getBytes().length);
        writeBuffer.clear();
        writeBuffer.put(message.getBytes());
        writeBuffer.flip();
        while (writeBuffer.hasRemaining()) {
            getSocket().write(writeBuffer);
        }
        System.out.println("Send Message:" + message);
    }

    public int getMaxRead() {
        return maxRead;
    }

    public int getMaxWrite() {
        return maxWrite;
    }

    public SocketChannel getSocket() {
        return socket;
    }

    public void setSocket(SocketChannel socket) {
        this.socket = socket;
    }

    public void setMaxWrite(int maxWrite) {
        this.maxWrite = maxWrite;
    }

    public void setMaxRead(int maxRead) {
        this.maxRead = maxRead;
    }
}