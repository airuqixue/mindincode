package com.mindincode.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by madic on 16-12-13.
 */
public class Handler implements Runnable {
    public final static int MAXIN = 4096;
    public final static int MAXOUT = 4096;
    final SocketChannel socket;
    final SelectionKey key;
    ByteBuffer input = ByteBuffer.allocate(MAXIN);
    ByteBuffer output = ByteBuffer.allocate(MAXOUT);
    static final int READING = 0, SENDING = 1;
    int state = READING;
    Handler(Selector selector, SocketChannel socketChannel) throws IOException{
        socket = socketChannel;
        socketChannel.configureBlocking(false);
        //Optinally try first read now
        key = socket.register(selector,READING);
        key.attach(this);
        key.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    boolean inputIsComplete(){
        return true;
    }

    boolean outputIsComplete(){
        return true;
    }

    public void run() {
        try{
            if(state == READING) read();
            else if(state == SENDING) send();
        }catch (IOException ex){/*...*/}
    }
    void read() throws IOException{
        System.out.println("reading from input ...");
        socket.read(input);
        if(inputIsComplete()){
            process();
            state = SENDING;
            //NOrmally also do first write now
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    void send() throws IOException{

        String s = "received from client";
        output.clear();
        output.put(s.getBytes());
        output.flip();
        int writeBytes = socket.write(output);
        System.out.println("writting "+writeBytes+" bytes to output...");
        if(outputIsComplete()){
            key.cancel();
        }
    }
    private void process(){
        /*...*/
      System.out.println("processing in handler "+ this.toString());
      input.flip();
      byte[] bytes = new byte[input.limit()];
      int i = 0;
      while(input.hasRemaining()) {
          bytes[i] = input.get();
                  i++;
      }
      System.out.println(new String(bytes));
     // System.out.println(input.asCharBuffer().toString());
    }
}
