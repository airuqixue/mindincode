package com.mindincode.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by madic on 16-12-13.
 */
public class Reactor implements Runnable{
    final Selector selector;
    final ServerSocketChannel serverSocket;

    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey key = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new Acceptor(serverSocket,selector));
    }
    /**
    Alternativel, use ecplicit SPI provider：
     <code>SelectorProvider provider = SelectorProvider.provider()</code>
     <code>selector = provider.openSelector();</code>
     <code>serverSocket = provider.openServerSocketChannel();</code>
     */
    public void run() { //normall in new Thread
        try{
            while(!Thread.interrupted()){
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while(it.hasNext()){
                    dispatch(it.next());
                }
                selected.clear();
            }
        }catch(IOException ex){
            /* ... */
        }
    }

    void dispatch(SelectionKey key) {
        Runnable r = (Runnable)(key.attachment());
        if(r!=null){
            System.out.println("dispatching key:"+key.toString());
            r.run();
        }
    }
}
