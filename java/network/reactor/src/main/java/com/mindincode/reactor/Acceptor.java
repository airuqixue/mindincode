package com.mindincode.reactor;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by madic on 16-12-13.
 */
public class Acceptor implements Runnable {
    private ServerSocketChannel serverSocket;
    private Selector selector;
    public Acceptor(ServerSocketChannel serverSocket,Selector selector){
        this.serverSocket = serverSocket;
        this.selector = selector;
    }
    public void run() {
        try{
            SocketChannel channel = serverSocket.accept();
            if(channel != null){
                new Handler(selector, channel);
            }
        }catch(IOException ex){
            /* ... */
        }
    }
}
