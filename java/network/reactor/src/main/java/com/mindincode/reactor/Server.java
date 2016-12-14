package com.mindincode.reactor;

import java.io.IOException;

/**
 * Created by madic on 16-12-13.
 */
public class Server {
    public static void main(String[] args)throws IOException{
        Reactor reactor = new Reactor(7894);
        reactor.run();
    }
}
